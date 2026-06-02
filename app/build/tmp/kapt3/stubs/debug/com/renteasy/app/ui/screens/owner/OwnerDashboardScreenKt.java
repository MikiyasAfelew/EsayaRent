package com.renteasy.app.ui.screens.owner;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000B\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u001a,\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u001a\u001c\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u000eH\u0007\u001a\u0016\u0010\u0010\u001a\u00020\u00012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u001a\u0018\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0005H\u0007\u001a<\u0010\u0014\u001a\u00020\u00012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u0007\u001a\u001e\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u001a\b\u0010\u001d\u001a\u00020\u0001H\u0007\u001a\u0010\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020 H\u0007\u00a8\u0006!"}, d2 = {"ActiveListingsCard", "", "modifier", "Landroidx/compose/ui/Modifier;", "count", "", "BookingRequestItem", "booking", "Lcom/renteasy/app/domain/model/Booking;", "onAccept", "Lkotlin/Function0;", "onReject", "EarningsCard", "totalEarnings", "", "trend", "EmptyListingsPlaceholder", "onAddListing", "OccupancyCard", "rate", "OwnerDashboardScreen", "onNavigateToVerification", "onNavigateToMyListings", "viewModel", "Lcom/renteasy/app/ui/screens/owner/OwnerDashboardViewModel;", "OwnerPropertyCard", "property", "Lcom/renteasy/app/domain/model/Property;", "onClick", "OwnerTopBar", "PropertyStatusChip", "status", "Lcom/renteasy/app/domain/model/PropertyStatus;", "app_debug"})
public final class OwnerDashboardScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void OwnerDashboardScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAddListing, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToVerification, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToMyListings, @org.jetbrains.annotations.NotNull()
    com.renteasy.app.ui.screens.owner.OwnerDashboardViewModel viewModel) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void OwnerTopBar() {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void EarningsCard(@org.jetbrains.annotations.NotNull()
    java.lang.String totalEarnings, @org.jetbrains.annotations.Nullable()
    java.lang.String trend) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ActiveListingsCard(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, int count) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void OccupancyCard(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, int rate) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void BookingRequestItem(@org.jetbrains.annotations.NotNull()
    com.renteasy.app.domain.model.Booking booking, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAccept, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onReject) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void OwnerPropertyCard(@org.jetbrains.annotations.NotNull()
    com.renteasy.app.domain.model.Property property, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PropertyStatusChip(@org.jetbrains.annotations.NotNull()
    com.renteasy.app.domain.model.PropertyStatus status) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void EmptyListingsPlaceholder(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAddListing) {
    }
}