package com.renteasy.app.ui.screens.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renteasy.app.domain.model.*
import com.renteasy.app.domain.repository.AuthRepository
import com.renteasy.app.domain.repository.BookingRepository
import com.renteasy.app.domain.repository.PropertyRepository
import com.renteasy.app.data.api.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

// API secret key is fetched securely via BuildConfig from local.properties
val CHAPA_TEST_SECRET_KEY = com.renteasy.app.BuildConfig.CHAPA_SECRET_KEY


@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val propertyRepository: PropertyRepository,
    private val bookingRepository: BookingRepository,
    private val authRepository: AuthRepository,
    private val chapaApiService: ChapaApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    private val currentUser = authRepository.currentUser


    fun loadProperty(propertyId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            propertyRepository.getPropertyById(propertyId)
                .combine(currentUser) { property, user ->
                    Pair(property, user)
                }
                .collect { (property, user) ->
                    if (property != null) {
                        val totalAmount = property.pricePerMonth // Simple formula for demo
                        _uiState.update {
                            it.copy(
                                property = property,
                                user = user,
                                totalAmount = totalAmount,
                                isLoading = false,
                                email = user?.email ?: "",
                                fullName = user?.fullName ?: "",
                                phone = user?.phone ?: ""
                            )
                        }
                    } else {
                        _uiState.update { it.copy(error = "Property not found", isLoading = false) }
                    }
                }
        }
    }

    fun updateEmail(email: String) { _uiState.update { it.copy(email = email) } }
    fun updateFullName(name: String) { _uiState.update { it.copy(fullName = name) } }
    fun updatePhone(phone: String) { _uiState.update { it.copy(phone = phone) } }

    // Simulation Setters
    fun updateActivePaymentStep(step: String) { _uiState.update { it.copy(activePaymentStep = step) } }
    fun updatePhoneInput(phone: String) { _uiState.update { it.copy(phoneInput = phone) } }
    fun updateOtpInput(otp: String) { _uiState.update { it.copy(otpInput = otp) } }
    fun updateCardNumber(num: String) { _uiState.update { it.copy(cardNumber = num) } }
    fun updateCardExpiry(expiry: String) { _uiState.update { it.copy(cardExpiry = expiry) } }
    fun updateCardCvv(cvv: String) { _uiState.update { it.copy(cardCvv = cvv) } }
    fun updateCardName(name: String) { _uiState.update { it.copy(cardName = name) } }
    fun updateSimulatedProgressMessage(msg: String) { _uiState.update { it.copy(simulatedProgressMessage = msg) } }

    fun initiateChapaPayment() {
        val state = _uiState.value
        val property = state.property ?: return
        val user = state.user ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(isProcessing = true, error = null) }

            val bookingId = UUID.randomUUID().toString()
            val newBooking = Booking(
                id = bookingId,
                propertyId = property.id,
                propertyTitle = property.title,
                propertyImageUrl = property.images.firstOrNull() ?: "https://images.unsplash.com/photo-1560518883-ce09059eeffa?auto=format&fit=crop&q=80&w=800",
                userId = user.id,
                ownerId = property.ownerId,
                startDate = System.currentTimeMillis(),
                endDate = System.currentTimeMillis() + (7L * 24L * 60L * 60L * 1000L), // 7 days stay
                totalPrice = state.totalAmount,
                status = BookingStatus.PENDING,
                paymentStatus = PaymentStatus.INITIATED
            )

            val result = bookingRepository.createBooking(newBooking)
            if (result.isSuccess) {
                if (CHAPA_TEST_SECRET_KEY.isBlank() || CHAPA_TEST_SECRET_KEY.startsWith("CHAPA_TEST_SEC_KEY-PLACEHOLDER")) {
                    // --- FALLBACK: Launch local interactive payment portal mockup ---
                    val checkoutUrl = "https://checkout.chapa.co/checkout/web/payment/${bookingId}"
                    _uiState.update {
                        it.copy(
                            isProcessing = false,
                            showChapaGateway = true,
                            checkoutUrl = checkoutUrl,
                            activeBookingId = bookingId,
                            isChapaSandboxApiMode = false,
                            activePaymentStep = "SELECT_METHOD",
                            phoneInput = "",
                            otpInput = "",
                            cardNumber = "",
                            cardExpiry = "",
                            cardCvv = "",
                            cardName = "",
                            simulatedProgressMessage = ""
                        )
                    }
                } else {
                    // --- ACTIVE: Connect with actual Chapa Developer Sandbox API ---
                    val names = state.fullName.trim().split("\\s+".toRegex())
                    val firstName = names.getOrNull(0) ?: "Rent"
                    val lastName = names.getOrNull(1) ?: "Easy"
                    
                    val chapaRequest = ChapaInitializeRequest(
                        amount = state.totalAmount.toDouble(),
                        email = state.email.ifBlank { "test@renteasy.app" },
                        first_name = firstName,
                        last_name = lastName,
                        tx_ref = bookingId,
                        callback_url = null,
                        return_url = "https://renteasy.app/payment-success",
                        customization = ChapaCustomization(
                            title = "RentEasy Booking",
                            description = "Payment for ${property.title}"
                        )
                    )

                    try {
                        val response = chapaApiService.initializeTransaction(
                            authorization = "Bearer $CHAPA_TEST_SECRET_KEY",
                            request = chapaRequest
                        )
                        if (response.isSuccessful && response.body()?.status == "success") {
                            val realCheckoutUrl = response.body()?.data?.checkout_url ?: ""
                            _uiState.update {
                                it.copy(
                                    isProcessing = false,
                                    showChapaGateway = true,
                                    checkoutUrl = realCheckoutUrl,
                                    activeBookingId = bookingId,
                                    isChapaSandboxApiMode = true
                                )
                            }
                        } else {
                            val errorBody = response.errorBody()?.string() ?: ""
                            android.util.Log.e("CheckoutViewModel", "Chapa Sandbox Failed: ${response.message()} - $errorBody. Rolling over to interactive local gateway.")
                            val checkoutUrl = "https://checkout.chapa.co/checkout/web/payment/${bookingId}"
                            _uiState.update {
                                it.copy(
                                    isProcessing = false,
                                    showChapaGateway = true,
                                    checkoutUrl = checkoutUrl,
                                    activeBookingId = bookingId,
                                    isChapaSandboxApiMode = false,
                                    activePaymentStep = "SELECT_METHOD",
                                    phoneInput = "",
                                    otpInput = "",
                                    cardNumber = "",
                                    cardExpiry = "",
                                    cardCvv = "",
                                    cardName = "",
                                    simulatedProgressMessage = ""
                                )
                            }
                        }
                    } catch (e: Exception) {
                        android.util.Log.e("CheckoutViewModel", "Chapa Connection Error: ${e.localizedMessage}. Rolling over to interactive local gateway.", e)
                        val checkoutUrl = "https://checkout.chapa.co/checkout/web/payment/${bookingId}"
                        _uiState.update {
                            it.copy(
                                isProcessing = false,
                                showChapaGateway = true,
                                checkoutUrl = checkoutUrl,
                                activeBookingId = bookingId,
                                isChapaSandboxApiMode = false,
                                activePaymentStep = "SELECT_METHOD",
                                phoneInput = "",
                                otpInput = "",
                                cardNumber = "",
                                cardExpiry = "",
                                cardCvv = "",
                                cardName = "",
                                simulatedProgressMessage = ""
                            )
                        }
                    }
                }
            } else {
                _uiState.update {
                    it.copy(
                        isProcessing = false,
                        error = result.exceptionOrNull()?.message ?: "Failed to initialize checkout"
                    )
                }
            }
        }
    }

    fun finalizeBookingStatus(success: Boolean) {
        val bookingId = _uiState.value.activeBookingId ?: return
        val isSandboxApiMode = _uiState.value.isChapaSandboxApiMode
        viewModelScope.launch {
            _uiState.update { it.copy(isProcessing = true) }
            if (success) {
                var paymentVerified = !isSandboxApiMode
                if (isSandboxApiMode) {
                    try {
                        val response = chapaApiService.verifyTransaction(
                            authorization = "Bearer $CHAPA_TEST_SECRET_KEY",
                            txRef = bookingId
                        )
                        if (response.isSuccessful && response.body()?.status == "success" && response.body()?.data?.status == "success") {
                            paymentVerified = true
                        } else {
                            val errorMsg = response.body()?.message ?: "Transaction verification pending or failed"
                            _uiState.update { 
                                it.copy(
                                    isProcessing = false, 
                                    error = "Payment Verification Failed: $errorMsg" 
                                ) 
                            }
                            return@launch
                        }
                    } catch (e: Exception) {
                        _uiState.update { 
                            it.copy(
                                    isProcessing = false, 
                                    error = "Verification network error: ${e.localizedMessage}" 
                                ) 
                            }
                            return@launch
                        }
                    }

                if (paymentVerified) {
                    val result = bookingRepository.updateBookingStatus(
                        id = bookingId,
                        status = BookingStatus.CONFIRMED,
                        paymentStatus = PaymentStatus.SUCCESS
                    )
                    if (result.isSuccess) {
                        _uiState.update { it.copy(isProcessing = false, showChapaGateway = false, paymentSuccess = true) }
                    } else {
                        _uiState.update { it.copy(isProcessing = false, error = "Failed to update booking state") }
                    }
                }
            } else {
                bookingRepository.updateBookingStatus(
                    id = bookingId,
                    status = BookingStatus.CANCELLED,
                    paymentStatus = PaymentStatus.FAILED
                )
                _uiState.update { it.copy(isProcessing = false, showChapaGateway = false) }
            }
        }
    }

    fun resetSuccessState() {
        _uiState.update { it.copy(paymentSuccess = false) }
    }
}

data class CheckoutUiState(
    val property: Property? = null,
    val user: User? = null,
    val totalAmount: Int = 0,
    val email: String = "",
    val fullName: String = "",
    val phone: String = "",
    val isLoading: Boolean = false,
    val isProcessing: Boolean = false,
    val showChapaGateway: Boolean = false,
    val checkoutUrl: String = "",
    val activeBookingId: String? = null,
    val paymentSuccess: Boolean = false,
    val error: String? = null,

    // Real API mode flag
    val isChapaSandboxApiMode: Boolean = false,

    // Simulation fields
    val activePaymentStep: String = "SELECT_METHOD",
    val phoneInput: String = "",
    val otpInput: String = "",
    val cardNumber: String = "",
    val cardExpiry: String = "",
    val cardCvv: String = "",
    val cardName: String = "",
    val simulatedProgressMessage: String = ""
)
