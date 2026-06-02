package com.renteasy.app.data.dto

import com.renteasy.app.domain.model.Booking
import com.renteasy.app.domain.model.BookingStatus
import com.renteasy.app.domain.model.PaymentStatus

data class BookingDto(
    val id: String = "",
    val propertyId: String = "",
    val propertyTitle: String = "",
    val propertyImageUrl: String = "",
    val userId: String = "",
    val ownerId: String = "",
    val startDate: Long = 0L,
    val endDate: Long = 0L,
    val totalPrice: Int = 0,
    val status: String = "PENDING",
    val paymentStatus: String = "UNPAID",
    val createdAt: Long = 0L
) {
    fun toDomain(): Booking = Booking(
        id = id,
        propertyId = propertyId,
        propertyTitle = propertyTitle,
        propertyImageUrl = propertyImageUrl,
        userId = userId,
        ownerId = ownerId,
        startDate = startDate,
        endDate = endDate,
        totalPrice = totalPrice,
        status = try { BookingStatus.valueOf(status) } catch (e: Exception) { BookingStatus.PENDING },
        paymentStatus = try { PaymentStatus.valueOf(paymentStatus) } catch (e: Exception) { PaymentStatus.UNPAID },
        createdAt = createdAt
    )

    companion object {
        fun fromDomain(b: Booking) = BookingDto(
            id = b.id,
            propertyId = b.propertyId,
            propertyTitle = b.propertyTitle,
            propertyImageUrl = b.propertyImageUrl,
            userId = b.userId,
            ownerId = b.ownerId,
            startDate = b.startDate,
            endDate = b.endDate,
            totalPrice = b.totalPrice,
            status = b.status.name,
            paymentStatus = b.paymentStatus.name,
            createdAt = b.createdAt
        )
    }
}
