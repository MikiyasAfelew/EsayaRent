package com.renteasy.app.ui.screens.owner;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0014\u0010\u0012\u001a\u00020\u000f2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0014J\u000e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0011J\u000e\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u001dJ\u000e\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u0011J\u000e\u0010 \u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\u0011J\u000e\u0010\"\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\u0011J\u000e\u0010$\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020\u0011J\u000e\u0010&\u001a\u00020\u000f2\u0006\u0010\'\u001a\u00020\u0011R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006("}, d2 = {"Lcom/renteasy/app/ui/screens/owner/AddPropertyViewModel;", "Landroidx/lifecycle/ViewModel;", "propertyRepository", "Lcom/renteasy/app/domain/repository/PropertyRepository;", "authRepository", "Lcom/renteasy/app/domain/repository/AuthRepository;", "(Lcom/renteasy/app/domain/repository/PropertyRepository;Lcom/renteasy/app/domain/repository/AuthRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/renteasy/app/ui/screens/owner/AddPropertyUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "addImage", "", "url", "", "submitProperty", "onSuccess", "Lkotlin/Function0;", "updateArea", "area", "updateBathrooms", "count", "", "updateBedrooms", "updateCategory", "category", "Lcom/renteasy/app/domain/model/PropertyCategory;", "updateDescription", "description", "updateDistrict", "district", "updateLocation", "location", "updatePrice", "price", "updateTitle", "title", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AddPropertyViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.renteasy.app.domain.repository.PropertyRepository propertyRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.renteasy.app.domain.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.renteasy.app.ui.screens.owner.AddPropertyUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.renteasy.app.ui.screens.owner.AddPropertyUiState> uiState = null;
    
    @javax.inject.Inject()
    public AddPropertyViewModel(@org.jetbrains.annotations.NotNull()
    com.renteasy.app.domain.repository.PropertyRepository propertyRepository, @org.jetbrains.annotations.NotNull()
    com.renteasy.app.domain.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.renteasy.app.ui.screens.owner.AddPropertyUiState> getUiState() {
        return null;
    }
    
    public final void updateTitle(@org.jetbrains.annotations.NotNull()
    java.lang.String title) {
    }
    
    public final void updateDescription(@org.jetbrains.annotations.NotNull()
    java.lang.String description) {
    }
    
    public final void updateLocation(@org.jetbrains.annotations.NotNull()
    java.lang.String location) {
    }
    
    public final void updateDistrict(@org.jetbrains.annotations.NotNull()
    java.lang.String district) {
    }
    
    public final void updatePrice(@org.jetbrains.annotations.NotNull()
    java.lang.String price) {
    }
    
    public final void updateCategory(@org.jetbrains.annotations.NotNull()
    com.renteasy.app.domain.model.PropertyCategory category) {
    }
    
    public final void updateBedrooms(int count) {
    }
    
    public final void updateBathrooms(int count) {
    }
    
    public final void updateArea(@org.jetbrains.annotations.NotNull()
    java.lang.String area) {
    }
    
    public final void addImage(@org.jetbrains.annotations.NotNull()
    java.lang.String url) {
    }
    
    public final void submitProperty(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess) {
    }
}