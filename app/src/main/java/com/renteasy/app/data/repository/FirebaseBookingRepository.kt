package com.renteasy.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.renteasy.app.domain.model.Booking
import com.renteasy.app.domain.model.BookingStatus
import com.renteasy.app.domain.model.PaymentStatus
import com.renteasy.app.domain.repository.BookingRepository
import com.renteasy.app.data.dto.BookingDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FirebaseBookingRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : BookingRepository {

    override suspend fun createBooking(booking: Booking): Result<Unit> {
        return try {
            // Rule 1: A Rented Property Cannot Be Rented Again (Overlap checking)
            val activeBookingsSnapshot = firestore.collection("bookings")
                .whereEqualTo("propertyId", booking.propertyId)
                .get()
                .await()
            
            val activeBookings = activeBookingsSnapshot.toObjects(BookingDto::class.java).map { it.toDomain() }
            
            // Check for overlap with active/confirmed or active pending bookings (less than 10 minutes old)
            val hasOverlap = activeBookings.any { existing ->
                val isPendingAndActive = existing.status == BookingStatus.PENDING && 
                        (System.currentTimeMillis() - existing.startDate < 10 * 60 * 1000) // 10 minutes window
                val isConfirmed = existing.status == BookingStatus.CONFIRMED

                (isConfirmed || isPendingAndActive) &&
                (booking.startDate < existing.endDate && booking.endDate > existing.startDate)
            }
            
            if (hasOverlap) {
                return Result.failure(IllegalStateException("Property is already rented or reserved during the requested dates."))
            }

            // Rule 6: Update Property Status to RESERVED when a booking is created (pending)
            firestore.collection("properties")
                .document(booking.propertyId)
                .update("status", "RESERVED")
                .await()

            // Save the booking
            firestore.collection("bookings")
                .document(booking.id)
                .set(BookingDto.fromDomain(booking))
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getBookingsByRenter(renterId: String): Flow<List<Booking>> = callbackFlow {
        val subscription = firestore.collection("bookings")
            .whereEqualTo("userId", renterId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(emptyList())
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val bookings = snapshot.toObjects(BookingDto::class.java).mapNotNull { dto ->
                        try { dto.toDomain() } catch (e: Exception) { null }
                    }.sortedByDescending { it.createdAt }
                    trySend(bookings)
                }
            }
        awaitClose { subscription.remove() }
    }

    override fun getBookingsByOwner(ownerId: String): Flow<List<Booking>> = callbackFlow {
        val subscription = firestore.collection("bookings")
            .whereEqualTo("ownerId", ownerId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(emptyList())
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val bookings = snapshot.toObjects(BookingDto::class.java).mapNotNull { dto ->
                        try { dto.toDomain() } catch (e: Exception) { null }
                    }.sortedByDescending { it.createdAt }
                    trySend(bookings)
                }
            }
        awaitClose { subscription.remove() }
    }

    override fun getBookingById(id: String): Flow<Booking?> = callbackFlow {
        val subscription = firestore.collection("bookings")
            .document(id)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(null)
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    val booking = try {
                        snapshot.toObject(BookingDto::class.java)?.toDomain()
                    } catch (e: Exception) {
                        null
                    }
                    trySend(booking)
                } else {
                    trySend(null)
                }
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun updateBookingStatus(id: String, status: BookingStatus, paymentStatus: PaymentStatus): Result<Unit> {
        return try {
            val updates = mutableMapOf<String, Any>(
                "status" to status.name,
                "paymentStatus" to paymentStatus.name
            )

            if (paymentStatus == PaymentStatus.SUCCESS || status == BookingStatus.CONFIRMED) {
                // Fetch current booking total to calculate platform commission (Rule 4)
                val bookingDoc = firestore.collection("bookings").document(id).get().await()
                val totalPrice = bookingDoc.getLong("totalPrice") ?: 0L
                val commission = (totalPrice * 0.05).toInt()
                val ownerAmount = totalPrice.toInt() - commission
                
                updates["commissionAmount"] = commission
                updates["ownerAmount"] = ownerAmount
                updates["rentAmount"] = totalPrice.toInt()
                
                // Rule 6: Transition property availability to RENTED
                val propertyId = bookingDoc.getString("propertyId")
                if (propertyId != null) {
                    firestore.collection("properties")
                        .document(propertyId)
                        .update("status", "RENTED")
                        .await()
                }
            } else if (status == BookingStatus.CANCELLED) {
                // Rule 6: Transition property availability back to AVAILABLE upon cancellation
                val bookingDoc = firestore.collection("bookings").document(id).get().await()
                val propertyId = bookingDoc.getString("propertyId")
                if (propertyId != null) {
                    firestore.collection("properties")
                        .document(propertyId)
                        .update("status", "AVAILABLE")
                        .await()
                }
            }

            firestore.collection("bookings").document(id).update(updates).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

