package com.renteasy.app.domain.model

enum class BookingStatus {
    PENDING,    // waiting for payment
    CONFIRMED,  // payment done
    COMPLETED,  // stay finished
    CANCELLED   // cancelled
}

enum class PaymentStatus {
    UNPAID, INITIATED, SUCCESS, FAILED
}

data class Booking(
    val id: String,
    val propertyId: String,
    val propertyTitle: String,
    val propertyImageUrl: String,
    val userId: String,
    val ownerId: String,
    val startDate: Long,
    val endDate: Long,
    val totalPrice: Int,
    val status: BookingStatus = BookingStatus.PENDING,
    val paymentStatus: PaymentStatus = PaymentStatus.UNPAID,
    val createdAt: Long = System.currentTimeMillis()
)
