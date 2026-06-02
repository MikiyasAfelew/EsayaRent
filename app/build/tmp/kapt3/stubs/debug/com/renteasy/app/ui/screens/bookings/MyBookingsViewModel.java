package com.renteasy.app.ui.screens.bookings;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR#\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\b\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u000b\u00a8\u0006\u0016"}, d2 = {"Lcom/renteasy/app/ui/screens/bookings/MyBookingsViewModel;", "Landroidx/lifecycle/ViewModel;", "bookingRepository", "Lcom/renteasy/app/domain/repository/BookingRepository;", "authRepository", "Lcom/renteasy/app/domain/repository/AuthRepository;", "(Lcom/renteasy/app/domain/repository/BookingRepository;Lcom/renteasy/app/domain/repository/AuthRepository;)V", "currentUser", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/renteasy/app/domain/model/User;", "getCurrentUser", "()Lkotlinx/coroutines/flow/StateFlow;", "renterBookings", "", "Lcom/renteasy/app/domain/model/Booking;", "getRenterBookings$annotations", "()V", "getRenterBookings", "cancelBooking", "", "bookingId", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class MyBookingsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.renteasy.app.domain.repository.BookingRepository bookingRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.renteasy.app.domain.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.renteasy.app.domain.model.User> currentUser = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.renteasy.app.domain.model.Booking>> renterBookings = null;
    
    @javax.inject.Inject()
    public MyBookingsViewModel(@org.jetbrains.annotations.NotNull()
    com.renteasy.app.domain.repository.BookingRepository bookingRepository, @org.jetbrains.annotations.NotNull()
    com.renteasy.app.domain.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.renteasy.app.domain.model.User> getCurrentUser() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.renteasy.app.domain.model.Booking>> getRenterBookings() {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
    @java.lang.Deprecated()
    public static void getRenterBookings$annotations() {
    }
    
    public final void cancelBooking(@org.jetbrains.annotations.NotNull()
    java.lang.String bookingId) {
    }
}