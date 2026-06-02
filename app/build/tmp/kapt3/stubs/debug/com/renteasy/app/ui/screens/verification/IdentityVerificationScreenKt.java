package com.renteasy.app.ui.screens.verification;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\u001aP\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u001a\u0010\b\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n0\t2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\rH\u0003\u001a.\u0010\u000e\u001a\u00020\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\b\b\u0002\u0010\u0012\u001a\u00020\u0013H\u0007\u001a\u0018\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u0003H\u0003\u001a<\u0010\u0016\u001a\u00020\u00012\b\u0010\u0017\u001a\u0004\u0018\u00010\u000b2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u0010H\u0003\u001a\u001a\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u00032\b\u0010\u001d\u001a\u0004\u0018\u00010\u000bH\u0003\u001a\b\u0010\u001e\u001a\u00020\u0001H\u0007\u00a8\u0006\u001f"}, d2 = {"DocumentCardWithPicker", "", "title", "", "isRequired", "", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "slots", "", "Lkotlin/Pair;", "Landroid/net/Uri;", "onSlotClick", "Lkotlin/Function1;", "IdentityVerificationScreen", "onBack", "Lkotlin/Function0;", "onVerificationSubmitted", "viewModel", "Lcom/renteasy/app/ui/navigation/AuthViewModel;", "InfoRequirementRow", "text", "LivenessCardFunctional", "selfieUri", "onTakePhoto", "onPickFromGallery", "onRetake", "ReviewRow", "label", "uri", "TelebirrQuickVerify", "app_debug"})
public final class IdentityVerificationScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void IdentityVerificationScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onVerificationSubmitted, @org.jetbrains.annotations.NotNull()
    com.renteasy.app.ui.navigation.AuthViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void InfoRequirementRow(androidx.compose.ui.graphics.vector.ImageVector icon, java.lang.String text) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DocumentCardWithPicker(java.lang.String title, boolean isRequired, androidx.compose.ui.graphics.vector.ImageVector icon, java.util.List<? extends kotlin.Pair<java.lang.String, ? extends android.net.Uri>> slots, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSlotClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void LivenessCardFunctional(android.net.Uri selfieUri, kotlin.jvm.functions.Function0<kotlin.Unit> onTakePhoto, kotlin.jvm.functions.Function0<kotlin.Unit> onPickFromGallery, kotlin.jvm.functions.Function0<kotlin.Unit> onRetake) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ReviewRow(java.lang.String label, android.net.Uri uri) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void TelebirrQuickVerify() {
    }
}