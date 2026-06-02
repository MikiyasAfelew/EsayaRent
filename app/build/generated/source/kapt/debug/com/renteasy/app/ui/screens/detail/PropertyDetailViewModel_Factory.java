package com.renteasy.app.ui.screens.detail;

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
public final class PropertyDetailViewModel_Factory implements Factory<PropertyDetailViewModel> {
  private final Provider<PropertyRepository> repositoryProvider;

  private final Provider<FavoritesManager> favoritesManagerProvider;

  public PropertyDetailViewModel_Factory(Provider<PropertyRepository> repositoryProvider,
      Provider<FavoritesManager> favoritesManagerProvider) {
    this.repositoryProvider = repositoryProvider;
    this.favoritesManagerProvider = favoritesManagerProvider;
  }

  @Override
  public PropertyDetailViewModel get() {
    return newInstance(repositoryProvider.get(), favoritesManagerProvider.get());
  }

  public static PropertyDetailViewModel_Factory create(
      Provider<PropertyRepository> repositoryProvider,
      Provider<FavoritesManager> favoritesManagerProvider) {
    return new PropertyDetailViewModel_Factory(repositoryProvider, favoritesManagerProvider);
  }

  public static PropertyDetailViewModel newInstance(PropertyRepository repository,
      FavoritesManager favoritesManager) {
    return new PropertyDetailViewModel(repository, favoritesManager);
  }
}
