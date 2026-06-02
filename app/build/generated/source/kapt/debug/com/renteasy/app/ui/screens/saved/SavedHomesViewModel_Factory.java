package com.renteasy.app.ui.screens.saved;

import com.renteasy.app.data.FavoritesManager;
import com.renteasy.app.domain.repository.PropertyRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class SavedHomesViewModel_Factory implements Factory<SavedHomesViewModel> {
  private final Provider<PropertyRepository> propertyRepositoryProvider;

  private final Provider<FavoritesManager> favoritesManagerProvider;

  public SavedHomesViewModel_Factory(Provider<PropertyRepository> propertyRepositoryProvider,
      Provider<FavoritesManager> favoritesManagerProvider) {
    this.propertyRepositoryProvider = propertyRepositoryProvider;
    this.favoritesManagerProvider = favoritesManagerProvider;
  }

  @Override
  public SavedHomesViewModel get() {
    return newInstance(propertyRepositoryProvider.get(), favoritesManagerProvider.get());
  }

  public static SavedHomesViewModel_Factory create(
      Provider<PropertyRepository> propertyRepositoryProvider,
      Provider<FavoritesManager> favoritesManagerProvider) {
    return new SavedHomesViewModel_Factory(propertyRepositoryProvider, favoritesManagerProvider);
  }

  public static SavedHomesViewModel newInstance(PropertyRepository propertyRepository,
      FavoritesManager favoritesManager) {
    return new SavedHomesViewModel(propertyRepository, favoritesManager);
  }
}
