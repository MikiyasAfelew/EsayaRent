package com.renteasy.app.ui.screens.detail;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\tJ\u0006\u0010\u0014\u001a\u00020\u0012R\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\rR\u0019\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/renteasy/app/ui/screens/detail/PropertyDetailViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/renteasy/app/domain/repository/PropertyRepository;", "favoritesManager", "Lcom/renteasy/app/data/FavoritesManager;", "(Lcom/renteasy/app/domain/repository/PropertyRepository;Lcom/renteasy/app/data/FavoritesManager;)V", "_propertyId", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "isFavourite", "Lkotlinx/coroutines/flow/StateFlow;", "", "()Lkotlinx/coroutines/flow/StateFlow;", "property", "Lcom/renteasy/app/domain/model/Property;", "getProperty", "setPropertyId", "", "id", "toggleFavourite", "app_debug"})
@kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class PropertyDetailViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.renteasy.app.domain.repository.PropertyRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.renteasy.app.data.FavoritesManager favoritesManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _propertyId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.renteasy.app.domain.model.Property> property = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isFavourite = null;
    
    @javax.inject.Inject()
    public PropertyDetailViewModel(@org.jetbrains.annotations.NotNull()
    com.renteasy.app.domain.repository.PropertyRepository repository, @org.jetbrains.annotations.NotNull()
    com.renteasy.app.data.FavoritesManager favoritesManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.renteasy.app.domain.model.Property> getProperty() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isFavourite() {
        return null;
    }
    
    public final void setPropertyId(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void toggleFavourite() {
    }
}