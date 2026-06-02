package com.renteasy.app.ui.screens.checkout;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0016\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u0006\u0010\u0018\u001a\u00020\u0015J\u000e\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u001bJ\u0006\u0010\u001c\u001a\u00020\u0015J\u000e\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u001bJ\u000e\u0010\u001f\u001a\u00020\u00152\u0006\u0010 \u001a\u00020\u001bJ\u000e\u0010!\u001a\u00020\u00152\u0006\u0010\"\u001a\u00020\u001bJ\u000e\u0010#\u001a\u00020\u00152\u0006\u0010$\u001a\u00020\u001bJ\u000e\u0010%\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\u001bJ\u000e\u0010\'\u001a\u00020\u00152\u0006\u0010(\u001a\u00020\u001bJ\u000e\u0010)\u001a\u00020\u00152\u0006\u0010$\u001a\u00020\u001bJ\u000e\u0010*\u001a\u00020\u00152\u0006\u0010+\u001a\u00020\u001bJ\u000e\u0010,\u001a\u00020\u00152\u0006\u0010-\u001a\u00020\u001bJ\u000e\u0010.\u001a\u00020\u00152\u0006\u0010-\u001a\u00020\u001bJ\u000e\u0010/\u001a\u00020\u00152\u0006\u00100\u001a\u00020\u001bR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u00061"}, d2 = {"Lcom/renteasy/app/ui/screens/checkout/CheckoutViewModel;", "Landroidx/lifecycle/ViewModel;", "propertyRepository", "Lcom/renteasy/app/domain/repository/PropertyRepository;", "bookingRepository", "Lcom/renteasy/app/domain/repository/BookingRepository;", "authRepository", "Lcom/renteasy/app/domain/repository/AuthRepository;", "chapaApiService", "Lcom/renteasy/app/data/api/ChapaApiService;", "(Lcom/renteasy/app/domain/repository/PropertyRepository;Lcom/renteasy/app/domain/repository/BookingRepository;Lcom/renteasy/app/domain/repository/AuthRepository;Lcom/renteasy/app/data/api/ChapaApiService;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/renteasy/app/ui/screens/checkout/CheckoutUiState;", "currentUser", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/renteasy/app/domain/model/User;", "uiState", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "finalizeBookingStatus", "", "success", "", "initiateChapaPayment", "loadProperty", "propertyId", "", "resetSuccessState", "updateActivePaymentStep", "step", "updateCardCvv", "cvv", "updateCardExpiry", "expiry", "updateCardName", "name", "updateCardNumber", "num", "updateEmail", "email", "updateFullName", "updateOtpInput", "otp", "updatePhone", "phone", "updatePhoneInput", "updateSimulatedProgressMessage", "msg", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class CheckoutViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.renteasy.app.domain.repository.PropertyRepository propertyRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.renteasy.app.domain.repository.BookingRepository bookingRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.renteasy.app.domain.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.renteasy.app.data.api.ChapaApiService chapaApiService = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.renteasy.app.ui.screens.checkout.CheckoutUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.renteasy.app.ui.screens.checkout.CheckoutUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.renteasy.app.domain.model.User> currentUser = null;
    
    @javax.inject.Inject()
    public CheckoutViewModel(@org.jetbrains.annotations.NotNull()
    com.renteasy.app.domain.repository.PropertyRepository propertyRepository, @org.jetbrains.annotations.NotNull()
    com.renteasy.app.domain.repository.BookingRepository bookingRepository, @org.jetbrains.annotations.NotNull()
    com.renteasy.app.domain.repository.AuthRepository authRepository, @org.jetbrains.annotations.NotNull()
    com.renteasy.app.data.api.ChapaApiService chapaApiService) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.renteasy.app.ui.screens.checkout.CheckoutUiState> getUiState() {
        return null;
    }
    
    public final void loadProperty(@org.jetbrains.annotations.NotNull()
    java.lang.String propertyId) {
    }
    
    public final void updateEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email) {
    }
    
    public final void updateFullName(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final void updatePhone(@org.jetbrains.annotations.NotNull()
    java.lang.String phone) {
    }
    
    public final void updateActivePaymentStep(@org.jetbrains.annotations.NotNull()
    java.lang.String step) {
    }
    
    public final void updatePhoneInput(@org.jetbrains.annotations.NotNull()
    java.lang.String phone) {
    }
    
    public final void updateOtpInput(@org.jetbrains.annotations.NotNull()
    java.lang.String otp) {
    }
    
    public final void updateCardNumber(@org.jetbrains.annotations.NotNull()
    java.lang.String num) {
    }
    
    public final void updateCardExpiry(@org.jetbrains.annotations.NotNull()
    java.lang.String expiry) {
    }
    
    public final void updateCardCvv(@org.jetbrains.annotations.NotNull()
    java.lang.String cvv) {
    }
    
    public final void updateCardName(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final void updateSimulatedProgressMessage(@org.jetbrains.annotations.NotNull()
    java.lang.String msg) {
    }
    
    public final void initiateChapaPayment() {
    }
    
    public final void finalizeBookingStatus(boolean success) {
    }
    
    public final void resetSuccessState() {
    }
}