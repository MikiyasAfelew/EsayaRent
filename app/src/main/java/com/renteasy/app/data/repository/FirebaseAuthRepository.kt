package com.renteasy.app.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.renteasy.app.domain.model.User
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.renteasy.app.domain.model.UserVerificationLevel
import com.renteasy.app.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    @ApplicationContext private val context: Context
) : AuthRepository {

    private val _currentUser = MutableStateFlow<User?>(null)
    override val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    
    override val hasUserSession: Boolean
        get() = auth.currentUser != null

    private var userListenerRegistration: com.google.firebase.firestore.ListenerRegistration? = null

    init {
        auth.addAuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser == null) {
                userListenerRegistration?.remove()
                userListenerRegistration = null
                _currentUser.value = null
            } else {
                if (firebaseUser.isEmailVerified) {
                    fetchUserDetails(firebaseUser.uid)
                } else {
                    auth.signOut()
                    userListenerRegistration?.remove()
                    userListenerRegistration = null
                    _currentUser.value = null
                }
            }
        }
    }

    private fun fetchUserDetails(uid: String) {
        userListenerRegistration?.remove()
        userListenerRegistration = firestore.collection("users").document(uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                if (snapshot != null && snapshot.exists()) {
                    try {
                        val levelStr = snapshot.getString("verificationLevel") ?: "BASIC"
                        val level = try { 
                            UserVerificationLevel.valueOf(levelStr) 
                        } catch (e: Exception) { 
                            UserVerificationLevel.BASIC 
                        }

                        val user = User(
                            id = uid,
                            fullName = snapshot.getString("fullName") ?: "Unknown",
                            email = snapshot.getString("email"),
                            phone = snapshot.getString("phone") ?: "",
                            location = snapshot.getString("location"),
                            avatarUrl = snapshot.getString("avatarUrl"),
                            isOwner = snapshot.getBoolean("isOwner") ?: false,
                            verificationLevel = level
                        )
                        _currentUser.value = user
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
    }


    override suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null && !user.isEmailVerified) {
                // Sign out immediately if email is not verified
                auth.signOut()
                return Result.failure(Exception("EMAIL_NOT_VERIFIED"))
            }
            Result.success(Unit)
        } catch (e: FirebaseAuthInvalidUserException) {
            Result.failure(Exception("USER_NOT_FOUND"))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("WRONG_PASSWORD"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUp(
        fullName: String,
        email: String,
        phone: String,
        password: String,
        isOwner: Boolean
    ): Result<Unit> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: throw Exception("User creation failed")
            
            val userMap = hashMapOf(
                "fullName" to fullName,
                "email" to email,
                "phone" to phone,
                "isOwner" to isOwner,
                "verificationLevel" to "BASIC",
                "joinedAt" to System.currentTimeMillis()
            )
            
            firestore.collection("users").document(firebaseUser.uid).set(userMap).await()
            
            // Send verification email
            firebaseUser.sendEmailVerification().await()
            
            // Sign out immediately so they cannot enter the app without verification
            auth.signOut()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendEmailVerification(): Result<Unit> {
        return try {
            auth.currentUser?.sendEmailVerification()?.await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun isEmailVerified(): Boolean {
        auth.currentUser?.reload()?.await()
        return auth.currentUser?.isEmailVerified ?: false
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun updateProfile(fullName: String, phone: String, location: String): Result<Unit> {
        return try {
            val user = auth.currentUser ?: throw Exception("User not authenticated")
            val updates = mapOf<String, Any>(
                "fullName" to fullName,
                "phone" to phone,
                "location" to location
            )
            firestore.collection("users").document(user.uid).update(updates).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun uploadAvatar(uri: String): Result<String> {
        return try {
            val user = auth.currentUser ?: throw Exception("User not authenticated")
            val androidUri = android.net.Uri.parse(uri)

            var finalUrl: String

            try {
                // Attempt 1: Upload to Firebase Storage
                val ref = FirebaseStorage.getInstance().reference.child("avatars/${user.uid}.jpg")
                val inputStream = context.contentResolver.openInputStream(androidUri)
                    ?: throw Exception("Cannot read selected image")
                ref.putStream(inputStream).await()
                inputStream.close()
                finalUrl = ref.downloadUrl.await().toString()
            } catch (storageError: Exception) {
                android.util.Log.w("FirebaseAuthRepo", "Storage upload failed, using memory-safe Base64 fallback: ${storageError.message}")
                
                // Attempt 2: Memory-safe compression & Base64 encoding
                val maxDimension = 300
                val resolver = context.contentResolver
                
                // Decode bounds first to prevent OutOfMemoryError
                val options = android.graphics.BitmapFactory.Options().apply {
                    inJustDecodeBounds = true
                }
                resolver.openInputStream(androidUri)?.use { input ->
                    android.graphics.BitmapFactory.decodeStream(input, null, options)
                }
                
                var inSampleSize = 1
                val srcWidth = options.outWidth
                val srcHeight = options.outHeight
                if (srcWidth > maxDimension || srcHeight > maxDimension) {
                    val halfWidth = srcWidth / 2
                    val halfHeight = srcHeight / 2
                    while ((halfWidth / inSampleSize) >= maxDimension && (halfHeight / inSampleSize) >= maxDimension) {
                        inSampleSize *= 2
                    }
                }
                
                val decodeOptions = android.graphics.BitmapFactory.Options().apply {
                    this.inSampleSize = inSampleSize
                }
                val bitmap = resolver.openInputStream(androidUri)?.use { input ->
                    android.graphics.BitmapFactory.decodeStream(input, null, decodeOptions)
                } ?: throw Exception("Failed to decode image bitmap")
                
                val scaledBitmap = if (bitmap.width > maxDimension || bitmap.height > maxDimension) {
                    val scale = maxDimension.toFloat() / maxOf(bitmap.width, bitmap.height)
                    android.graphics.Bitmap.createScaledBitmap(
                        bitmap,
                        (bitmap.width * scale).toInt(),
                        (bitmap.height * scale).toInt(),
                        true
                    )
                } else bitmap
                
                val baos = java.io.ByteArrayOutputStream()
                scaledBitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 60, baos)
                val base64 = android.util.Base64.encodeToString(baos.toByteArray(), android.util.Base64.NO_WRAP)
                finalUrl = "data:image/jpeg;base64,$base64"
            }

            // Save the URL (either https:// or data:image) to Firestore
            firestore.collection("users").document(user.uid)
                .update("avatarUrl", finalUrl).await()
            Result.success(finalUrl)
        } catch (e: Exception) {
            android.util.Log.e("FirebaseAuthRepo", "Avatar upload failed completely: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun updateVerificationLevel(level: com.renteasy.app.domain.model.UserVerificationLevel): Result<Unit> {
        return try {
            val user = auth.currentUser ?: throw Exception("User not authenticated")
            val updates = mapOf<String, Any>(
                "verificationLevel" to level.name
            )
            firestore.collection("users").document(user.uid).update(updates).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
