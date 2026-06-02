package com.renteasy.app.ui.screens.bookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renteasy.app.domain.model.Booking
import com.renteasy.app.domain.model.BookingStatus
import com.renteasy.app.domain.model.PaymentStatus
import com.renteasy.app.domain.repository.AuthRepository
import com.renteasy.app.domain.repository.BookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyBookingsViewModel @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    val currentUser = authRepository.currentUser

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val renterBookings: StateFlow<List<Booking>> = currentUser
        .flatMapLatest { user ->
            if (user == null) flowOf(emptyList())
            else bookingRepository.getBookingsByRenter(user.id)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun cancelBooking(bookingId: String) {
        viewModelScope.launch {
            bookingRepository.updateBookingStatus(
                id = bookingId,
                status = BookingStatus.CANCELLED,
                paymentStatus = PaymentStatus.FAILED
            )
        }
    }
}
