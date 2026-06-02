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
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun register(fullName: String, email: String, phone: String, pass: String, isOwner: Boolean, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = RegisterUiState.Loading
            repository.signUp(fullName, email, phone, pass, isOwner)
                .onSuccess {
                    _uiState.value = RegisterUiState.Success
                    onSuccess()
                }
                .onFailure { error ->
                    _uiState.value = RegisterUiState.Error(error.message ?: "Registration failed")
                }
        }
    }

    fun clearState() {
        _uiState.value = RegisterUiState.Idle
    }
}

sealed class RegisterUiState {
    object Idle : RegisterUiState()
    object Loading : RegisterUiState()
    object Success : RegisterUiState()
    data class Error(val message: String) : RegisterUiState()
}
