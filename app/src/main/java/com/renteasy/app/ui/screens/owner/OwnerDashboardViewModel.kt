package com.renteasy.app.ui.screens.owner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renteasy.app.domain.model.Property
import com.renteasy.app.domain.model.PropertyStatus
import com.renteasy.app.domain.repository.AuthRepository
import com.renteasy.app.domain.repository.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.renteasy.app.domain.model.Booking
import com.renteasy.app.domain.model.BookingStatus
import com.renteasy.app.domain.model.PaymentStatus
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnerDashboardViewModel @Inject constructor(
    private val propertyRepository: PropertyRepository,
    private val bookingRepository: com.renteasy.app.domain.repository.BookingRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val currentUser = authRepository.currentUser

    val ownerName: StateFlow<String> = currentUser.map { user ->
        user?.fullName ?: "Owner"
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Owner")

    val verificationLevel: StateFlow<com.renteasy.app.domain.model.UserVerificationLevel> = currentUser.map { user ->
        user?.verificationLevel ?: com.renteasy.app.domain.model.UserVerificationLevel.BASIC
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), com.renteasy.app.domain.model.UserVerificationLevel.BASIC)

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val pendingBookings: StateFlow<List<Booking>> = currentUser
        .flatMapLatest { user ->
            if (user == null) flowOf(emptyList())
            else bookingRepository.getBookingsByOwner(user.id).map { list ->
                list.filter { it.status == BookingStatus.PENDING }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val totalEarnings: StateFlow<Int> = currentUser
        .flatMapLatest { user ->
            if (user == null) flowOf(0)
            else bookingRepository.getBookingsByOwner(user.id).map { bookings ->
                val gross = bookings.filter { it.status == BookingStatus.CONFIRMED || it.status == BookingStatus.COMPLETED }
                    .sumOf { it.totalPrice }
                // Deduct 5% RentEasy system cut — owner receives 95%
                (gross * 0.95).toInt()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val ownerProperties: StateFlow<List<Property>> = currentUser
        .flatMapLatest { user ->
            if (user == null) flowOf(emptyList())
            else propertyRepository.getPropertiesByOwner(user.id)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val occupancyRate: StateFlow<Int> = currentUser
        .flatMapLatest { user ->
            if (user == null) flowOf(0)
            else {
                combine(
                    propertyRepository.getPropertiesByOwner(user.id),
                    bookingRepository.getBookingsByOwner(user.id)
                ) { properties, bookings ->
                    val approvedProps = properties.filter { it.status == PropertyStatus.APPROVED }
                    if (approvedProps.isEmpty()) 0
                    else {
                        val activeBookedPropIds = bookings.filter { it.status == BookingStatus.CONFIRMED || it.status == BookingStatus.COMPLETED }
                            .map { it.propertyId }
                            .toSet()
                        val occupiedCount = approvedProps.count { it.id in activeBookedPropIds }
                        (occupiedCount * 100) / approvedProps.size
                    }
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun acceptBooking(bookingId: String) {
        viewModelScope.launch {
            bookingRepository.updateBookingStatus(
                id = bookingId,
                status = BookingStatus.CONFIRMED,
                paymentStatus = PaymentStatus.SUCCESS
            )
        }
    }

    fun rejectBooking(bookingId: String) {
        viewModelScope.launch {
            bookingRepository.updateBookingStatus(
                id = bookingId,
                status = BookingStatus.CANCELLED,
                paymentStatus = PaymentStatus.FAILED
            )
        }
    }

    fun approveProperty(propertyId: String) {
        viewModelScope.launch {
            propertyRepository.updatePropertyStatus(propertyId, PropertyStatus.APPROVED)
        }
    }

    fun deleteProperty(propertyId: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = propertyRepository.deleteProperty(propertyId)
            onResult(result.isSuccess)
        }
    }

    fun updateProperty(property: Property, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = propertyRepository.updateProperty(property)
            onResult(result.isSuccess)
        }
    }

    val pendingCount: StateFlow<Int> = ownerProperties.map { list ->
        list.count { it.status == PropertyStatus.PENDING }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val approvedCount: StateFlow<Int> = ownerProperties.map { list ->
        list.count { it.status == PropertyStatus.APPROVED }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
}
