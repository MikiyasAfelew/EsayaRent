package com.renteasy.app.domain.model

data class Review(
    val id: String,
    val bookingId: String,
    val propertyId: String,
    val userId: String,
    val userName: String,
    val userAvatarUrl: String? = null,
    val rating: Int, // 1-5
    val comment: String,
    val createdAt: Long = System.currentTimeMillis()
)
