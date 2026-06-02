package com.renteasy.app.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renteasy.app.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            repository.signIn(email, password)
                .onSuccess {
                    _uiState.value = LoginUiState.Success
                }
                .onFailure { error ->
                    val message = when (error.message) {
                        "EMAIL_NOT_VERIFIED" -> "Please verify your email before signing in. Check your inbox for the link."
                        "USER_NOT_FOUND" -> "No account found with this email. Please register first."
                        "WRONG_PASSWORD" -> "Incorrect password. Please try again."
                        else -> "Sign in failed. Please check your credentials and connection."
                    }
                    _uiState.value = LoginUiState.Error(message)
                }
        }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            repository.sendPasswordResetEmail(email)
                .onSuccess {
                    _uiState.value = LoginUiState.PasswordResetSent
                }
                .onFailure { error ->
                    _uiState.value = LoginUiState.Error(error.message ?: "Failed to send reset email")
                }
        }
    }

    fun resendVerification() {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            repository.sendEmailVerification()
                .onSuccess {
                    _uiState.value = LoginUiState.VerificationEmailSent
                }
                .onFailure { error ->
                    _uiState.value = LoginUiState.Error(error.message ?: "Failed to resend verification email")
                }
        }
    }

    fun clearState() {
        _uiState.value = LoginUiState.Idle
    }
}

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object PasswordResetSent : LoginUiState()
    object VerificationEmailSent : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}
