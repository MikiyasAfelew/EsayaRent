package com.renteasy.app.domain.repository

import com.renteasy.app.domain.model.Booking
import com.renteasy.app.domain.model.BookingStatus
import com.renteasy.app.domain.model.PaymentStatus
import kotlinx.coroutines.flow.Flow

interface BookingRepository {
    suspend fun createBooking(booking: Booking): Result<Unit>
    fun getBookingsByRenter(renterId: String): Flow<List<Booking>>
    fun getBookingsByOwner(ownerId: String): Flow<List<Booking>>
    fun getBookingById(id: String): Flow<Booking?>
    suspend fun updateBookingStatus(id: String, status: BookingStatus, paymentStatus: PaymentStatus): Result<Unit>
}
