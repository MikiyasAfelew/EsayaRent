package com.renteasy.app.ui.screens.bookings;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u001aJ\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052*\u0010\u0006\u001a&\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001aN\u0010\t\u001a\u00020\u00012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052*\u0010\u0006\u001a&\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00072\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0007\u001a\u001e\u0010\r\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u001e\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u0010\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0007\u001a\b\u0010\u0011\u001a\u00020\u0001H\u0007\u00a8\u0006\u0012"}, d2 = {"BookingCard", "", "booking", "Lcom/renteasy/app/domain/model/Booking;", "onCancelClick", "Lkotlin/Function0;", "onContactOwner", "Lkotlin/Function5;", "", "MyBookingsScreen", "onSignInRequired", "viewModel", "Lcom/renteasy/app/ui/screens/bookings/MyBookingsViewModel;", "RatingDialog", "onDismiss", "ReceiptDialog", "RentPaymentReminderCard", "SecuredPaymentBanner", "app_debug"})
public final class MyBookingsScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void MyBookingsScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSignInRequired, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function5<? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onContactOwner, @org.jetbrains.annotations.NotNull()
    com.renteasy.app.ui.screens.bookings.MyBookingsViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void BookingCard(@org.jetbrains.annotations.NotNull()
    com.renteasy.app.domain.model.Booking booking, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCancelClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function5<? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onContactOwner) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ReceiptDialog(@org.jetbrains.annotations.NotNull()
    com.renteasy.app.domain.model.Booking booking, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SecuredPaymentBanner() {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void RentPaymentReminderCard(@org.jetbrains.annotations.NotNull()
    com.renteasy.app.domain.model.Booking booking) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void RatingDialog(@org.jetbrains.annotations.NotNull()
    com.renteasy.app.domain.model.Booking booking, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
}