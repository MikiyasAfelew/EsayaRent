package com.renteasy.app.ui.screens.checkout;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\bD\b\u0086\b\u0018\u00002\u00020\u0001B\u00e9\u0001\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\t\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\r\u0012\b\b\u0002\u0010\u000f\u001a\u00020\r\u0012\b\b\u0002\u0010\u0010\u001a\u00020\t\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\t\u0012\b\b\u0002\u0010\u0012\u001a\u00020\r\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\t\u0012\b\b\u0002\u0010\u0014\u001a\u00020\r\u0012\b\b\u0002\u0010\u0015\u001a\u00020\t\u0012\b\b\u0002\u0010\u0016\u001a\u00020\t\u0012\b\b\u0002\u0010\u0017\u001a\u00020\t\u0012\b\b\u0002\u0010\u0018\u001a\u00020\t\u0012\b\b\u0002\u0010\u0019\u001a\u00020\t\u0012\b\b\u0002\u0010\u001a\u001a\u00020\t\u0012\b\b\u0002\u0010\u001b\u001a\u00020\t\u0012\b\b\u0002\u0010\u001c\u001a\u00020\t\u00a2\u0006\u0002\u0010\u001dJ\u000b\u00106\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u00107\u001a\u00020\tH\u00c6\u0003J\u000b\u00108\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\t\u00109\u001a\u00020\rH\u00c6\u0003J\u000b\u0010:\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\t\u0010;\u001a\u00020\rH\u00c6\u0003J\t\u0010<\u001a\u00020\tH\u00c6\u0003J\t\u0010=\u001a\u00020\tH\u00c6\u0003J\t\u0010>\u001a\u00020\tH\u00c6\u0003J\t\u0010?\u001a\u00020\tH\u00c6\u0003J\t\u0010@\u001a\u00020\tH\u00c6\u0003J\u000b\u0010A\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010B\u001a\u00020\tH\u00c6\u0003J\t\u0010C\u001a\u00020\tH\u00c6\u0003J\t\u0010D\u001a\u00020\tH\u00c6\u0003J\t\u0010E\u001a\u00020\u0007H\u00c6\u0003J\t\u0010F\u001a\u00020\tH\u00c6\u0003J\t\u0010G\u001a\u00020\tH\u00c6\u0003J\t\u0010H\u001a\u00020\tH\u00c6\u0003J\t\u0010I\u001a\u00020\rH\u00c6\u0003J\t\u0010J\u001a\u00020\rH\u00c6\u0003J\t\u0010K\u001a\u00020\rH\u00c6\u0003J\u00ed\u0001\u0010L\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\r2\b\b\u0002\u0010\u000f\u001a\u00020\r2\b\b\u0002\u0010\u0010\u001a\u00020\t2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\u0012\u001a\u00020\r2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\u0014\u001a\u00020\r2\b\b\u0002\u0010\u0015\u001a\u00020\t2\b\b\u0002\u0010\u0016\u001a\u00020\t2\b\b\u0002\u0010\u0017\u001a\u00020\t2\b\b\u0002\u0010\u0018\u001a\u00020\t2\b\b\u0002\u0010\u0019\u001a\u00020\t2\b\b\u0002\u0010\u001a\u001a\u00020\t2\b\b\u0002\u0010\u001b\u001a\u00020\t2\b\b\u0002\u0010\u001c\u001a\u00020\tH\u00c6\u0001J\u0013\u0010M\u001a\u00020\r2\b\u0010N\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010O\u001a\u00020\u0007H\u00d6\u0001J\t\u0010P\u001a\u00020\tH\u00d6\u0001R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0015\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001fR\u0011\u0010\u001a\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001fR\u0011\u0010\u0019\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001fR\u0011\u0010\u001b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001fR\u0011\u0010\u0018\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001fR\u0011\u0010\u0010\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001fR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001fR\u0013\u0010\u0013\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001fR\u0011\u0010\n\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001fR\u0011\u0010\u0014\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010)R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010)R\u0011\u0010\u000e\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010)R\u0011\u0010\u0017\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001fR\u0011\u0010\u0012\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010)R\u0011\u0010\u000b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001fR\u0011\u0010\u0016\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\u001fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0011\u0010\u000f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010)R\u0011\u0010\u001c\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010\u001fR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u00105\u00a8\u0006Q"}, d2 = {"Lcom/renteasy/app/ui/screens/checkout/CheckoutUiState;", "", "property", "Lcom/renteasy/app/domain/model/Property;", "user", "Lcom/renteasy/app/domain/model/User;", "totalAmount", "", "email", "", "fullName", "phone", "isLoading", "", "isProcessing", "showChapaGateway", "checkoutUrl", "activeBookingId", "paymentSuccess", "error", "isChapaSandboxApiMode", "activePaymentStep", "phoneInput", "otpInput", "cardNumber", "cardExpiry", "cardCvv", "cardName", "simulatedProgressMessage", "(Lcom/renteasy/app/domain/model/Property;Lcom/renteasy/app/domain/model/User;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZZLjava/lang/String;Ljava/lang/String;ZLjava/lang/String;ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getActiveBookingId", "()Ljava/lang/String;", "getActivePaymentStep", "getCardCvv", "getCardExpiry", "getCardName", "getCardNumber", "getCheckoutUrl", "getEmail", "getError", "getFullName", "()Z", "getOtpInput", "getPaymentSuccess", "getPhone", "getPhoneInput", "getProperty", "()Lcom/renteasy/app/domain/model/Property;", "getShowChapaGateway", "getSimulatedProgressMessage", "getTotalAmount", "()I", "getUser", "()Lcom/renteasy/app/domain/model/User;", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class CheckoutUiState {
    @org.jetbrains.annotations.Nullable()
    private final com.renteasy.app.domain.model.Property property = null;
    @org.jetbrains.annotations.Nullable()
    private final com.renteasy.app.domain.model.User user = null;
    private final int totalAmount = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String email = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String fullName = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String phone = null;
    private final boolean isLoading = false;
    private final boolean isProcessing = false;
    private final boolean showChapaGateway = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String checkoutUrl = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String activeBookingId = null;
    private final boolean paymentSuccess = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    private final boolean isChapaSandboxApiMode = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String activePaymentStep = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String phoneInput = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String otpInput = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String cardNumber = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String cardExpiry = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String cardCvv = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String cardName = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String simulatedProgressMessage = null;
    
    public CheckoutUiState(@org.jetbrains.annotations.Nullable()
    com.renteasy.app.domain.model.Property property, @org.jetbrains.annotations.Nullable()
    com.renteasy.app.domain.model.User user, int totalAmount, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String fullName, @org.jetbrains.annotations.NotNull()
    java.lang.String phone, boolean isLoading, boolean isProcessing, boolean showChapaGateway, @org.jetbrains.annotations.NotNull()
    java.lang.String checkoutUrl, @org.jetbrains.annotations.Nullable()
    java.lang.String activeBookingId, boolean paymentSuccess, @org.jetbrains.annotations.Nullable()
    java.lang.String error, boolean isChapaSandboxApiMode, @org.jetbrains.annotations.NotNull()
    java.lang.String activePaymentStep, @org.jetbrains.annotations.NotNull()
    java.lang.String phoneInput, @org.jetbrains.annotations.NotNull()
    java.lang.String otpInput, @org.jetbrains.annotations.NotNull()
    java.lang.String cardNumber, @org.jetbrains.annotations.NotNull()
    java.lang.String cardExpiry, @org.jetbrains.annotations.NotNull()
    java.lang.String cardCvv, @org.jetbrains.annotations.NotNull()
    java.lang.String cardName, @org.jetbrains.annotations.NotNull()
    java.lang.String simulatedProgressMessage) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.renteasy.app.domain.model.Property getProperty() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.renteasy.app.domain.model.User getUser() {
        return null;
    }
    
    public final int getTotalAmount() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEmail() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFullName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPhone() {
        return null;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    public final boolean isProcessing() {
        return false;
    }
    
    public final boolean getShowChapaGateway() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCheckoutUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getActiveBookingId() {
        return null;
    }
    
    public final boolean getPaymentSuccess() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    public final boolean isChapaSandboxApiMode() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getActivePaymentStep() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPhoneInput() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getOtpInput() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCardNumber() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCardExpiry() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCardCvv() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCardName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSimulatedProgressMessage() {
        return null;
    }
    
    public CheckoutUiState() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.renteasy.app.domain.model.Property component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    public final boolean component12() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component13() {
        return null;
    }
    
    public final boolean component14() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component16() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component17() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component18() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component19() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.renteasy.app.domain.model.User component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component20() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component21() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component22() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    public final boolean component7() {
        return false;
    }
    
    public final boolean component8() {
        return false;
    }
    
    public final boolean component9() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.renteasy.app.ui.screens.checkout.CheckoutUiState copy(@org.jetbrains.annotations.Nullable()
    com.renteasy.app.domain.model.Property property, @org.jetbrains.annotations.Nullable()
    com.renteasy.app.domain.model.User user, int totalAmount, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String fullName, @org.jetbrains.annotations.NotNull()
    java.lang.String phone, boolean isLoading, boolean isProcessing, boolean showChapaGateway, @org.jetbrains.annotations.NotNull()
    java.lang.String checkoutUrl, @org.jetbrains.annotations.Nullable()
    java.lang.String activeBookingId, boolean paymentSuccess, @org.jetbrains.annotations.Nullable()
    java.lang.String error, boolean isChapaSandboxApiMode, @org.jetbrains.annotations.NotNull()
    java.lang.String activePaymentStep, @org.jetbrains.annotations.NotNull()
    java.lang.String phoneInput, @org.jetbrains.annotations.NotNull()
    java.lang.String otpInput, @org.jetbrains.annotations.NotNull()
    java.lang.String cardNumber, @org.jetbrains.annotations.NotNull()
    java.lang.String cardExpiry, @org.jetbrains.annotations.NotNull()
    java.lang.String cardCvv, @org.jetbrains.annotations.NotNull()
    java.lang.String cardName, @org.jetbrains.annotations.NotNull()
    java.lang.String simulatedProgressMessage) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}