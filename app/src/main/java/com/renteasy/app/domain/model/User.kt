package com.renteasy.app.domain.model

enum class UserVerificationLevel {
    BASIC,          // phone only
    PENDING_REVIEW, // documents submitted, awaiting admin verification
    ID_VERIFIED,    // ID documents verified
    FULLY_VERIFIED  // background check done
}

data class User(
    val id: String,
    val fullName: String,
    val phone: String,
    val email: String? = null,
    val location: String? = null,
    val avatarUrl: String? = null,
    val verificationLevel: UserVerificationLevel = UserVerificationLevel.BASIC,
    val isOwner: Boolean = false,
    val isAdmin: Boolean = false,
    val rating: Float = 0f,
    val joinedAt: Long = System.currentTimeMillis()
)
