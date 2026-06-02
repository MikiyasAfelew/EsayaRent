package com.renteasy.app.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renteasy.app.domain.model.User
import com.renteasy.app.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    val currentUser: StateFlow<User?> = repository.currentUser
    val hasUserSession: Boolean get() = repository.hasUserSession

    fun signOut() {
        viewModelScope.launch {
            repository.signOut()
        }
    }

    suspend fun updateVerificationLevel(level: com.renteasy.app.domain.model.UserVerificationLevel): Result<Unit> {
        return repository.updateVerificationLevel(level)
    }

    suspend fun uploadAvatar(uri: String): Result<String> {
        return repository.uploadAvatar(uri)
    }

    suspend fun updateProfile(fullName: String, phone: String, location: String): Result<Unit> {
        return repository.updateProfile(fullName, phone, location)
    }
}

