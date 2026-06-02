package com.renteasy.app.domain.repository

import com.renteasy.app.domain.model.User
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val currentUser: StateFlow<User?>
    val hasUserSession: Boolean
    suspend fun signIn(email: String, password: String): Result<Unit>
    suspend fun signUp(fullName: String, email: String, phone: String, password: String, isOwner: Boolean): Result<Unit>
    suspend fun sendPasswordResetEmail(email: String): Result<Unit>
    suspend fun sendEmailVerification(): Result<Unit>
    suspend fun isEmailVerified(): Boolean
    suspend fun updateProfile(fullName: String, phone: String, location: String): Result<Unit>
    suspend fun uploadAvatar(uri: String): Result<String>
    suspend fun updateVerificationLevel(level: com.renteasy.app.domain.model.UserVerificationLevel): Result<Unit>
    suspend fun signOut()
}
