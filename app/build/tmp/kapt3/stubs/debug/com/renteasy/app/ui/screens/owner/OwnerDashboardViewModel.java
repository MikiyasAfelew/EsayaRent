package com.renteasy.app.ui.screens.owner;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0015J\u000e\u0010+\u001a\u00020)2\u0006\u0010,\u001a\u00020\u0015J\"\u0010-\u001a\u00020)2\u0006\u0010,\u001a\u00020\u00152\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020)0/J\u000e\u00101\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0015J\"\u00102\u001a\u00020)2\u0006\u00103\u001a\u00020\u00192\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020)0/R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\rR\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\rR#\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u00180\n\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u001a\u0010\u0012\u001a\u0004\b\u001b\u0010\rR#\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001d0\u00180\n\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u001e\u0010\u0012\u001a\u0004\b\u001f\u0010\rR\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\u000e\n\u0000\u0012\u0004\b#\u0010\u0012\u001a\u0004\b$\u0010\rR\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\r\u00a8\u00064"}, d2 = {"Lcom/renteasy/app/ui/screens/owner/OwnerDashboardViewModel;", "Landroidx/lifecycle/ViewModel;", "propertyRepository", "Lcom/renteasy/app/domain/repository/PropertyRepository;", "bookingRepository", "Lcom/renteasy/app/domain/repository/BookingRepository;", "authRepository", "Lcom/renteasy/app/domain/repository/AuthRepository;", "(Lcom/renteasy/app/domain/repository/PropertyRepository;Lcom/renteasy/app/domain/repository/BookingRepository;Lcom/renteasy/app/domain/repository/AuthRepository;)V", "approvedCount", "Lkotlinx/coroutines/flow/StateFlow;", "", "getApprovedCount", "()Lkotlinx/coroutines/flow/StateFlow;", "currentUser", "Lcom/renteasy/app/domain/model/User;", "occupancyRate", "getOccupancyRate$annotations", "()V", "getOccupancyRate", "ownerName", "", "getOwnerName", "ownerProperties", "", "Lcom/renteasy/app/domain/model/Property;", "getOwnerProperties$annotations", "getOwnerProperties", "pendingBookings", "Lcom/renteasy/app/domain/model/Booking;", "getPendingBookings$annotations", "getPendingBookings", "pendingCount", "getPendingCount", "totalEarnings", "getTotalEarnings$annotations", "getTotalEarnings", "verificationLevel", "Lcom/renteasy/app/domain/model/UserVerificationLevel;", "getVerificationLevel", "acceptBooking", "", "bookingId", "approveProperty", "propertyId", "deleteProperty", "onResult", "Lkotlin/Function1;", "", "rejectBooking", "updateProperty", "property", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class OwnerDashboardViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.renteasy.app.domain.repository.PropertyRepository propertyRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.renteasy.app.domain.repository.BookingRepository bookingRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.renteasy.app.domain.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.renteasy.app.domain.model.User> currentUser = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> ownerName = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.renteasy.app.domain.model.UserVerificationLevel> verificationLevel = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.renteasy.app.domain.model.Booking>> pendingBookings = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> totalEarnings = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.renteasy.app.domain.model.Property>> ownerProperties = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> occupancyRate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> pendingCount = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> approvedCount = null;
    
    @javax.inject.Inject()
    public OwnerDashboardViewModel(@org.jetbrains.annotations.NotNull()
    com.renteasy.app.domain.repository.PropertyRepository propertyRepository, @org.jetbrains.annotations.NotNull()
    com.renteasy.app.domain.repository.BookingRepository bookingRepository, @org.jetbrains.annotations.NotNull()
    com.renteasy.app.domain.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getOwnerName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.renteasy.app.domain.model.UserVerificationLevel> getVerificationLevel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.renteasy.app.domain.model.Booking>> getPendingBookings() {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
    @java.lang.Deprecated()
    public static void getPendingBookings$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getTotalEarnings() {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
    @java.lang.Deprecated()
    public static void getTotalEarnings$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.renteasy.app.domain.model.Property>> getOwnerProperties() {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
    @java.lang.Deprecated()
    public static void getOwnerProperties$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getOccupancyRate() {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
    @java.lang.Deprecated()
    public static void getOccupancyRate$annotations() {
    }
    
    public final void acceptBooking(@org.jetbrains.annotations.NotNull()
    java.lang.String bookingId) {
    }
    
    public final void rejectBooking(@org.jetbrains.annotations.NotNull()
    java.lang.String bookingId) {
    }
    
    public final void approveProperty(@org.jetbrains.annotations.NotNull()
    java.lang.String propertyId) {
    }
    
    public final void deleteProperty(@org.jetbrains.annotations.NotNull()
    java.lang.String propertyId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onResult) {
    }
    
    public final void updateProperty(@org.jetbrains.annotations.NotNull()
    com.renteasy.app.domain.model.Property property, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onResult) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getPendingCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getApprovedCount() {
        return null;
    }
}