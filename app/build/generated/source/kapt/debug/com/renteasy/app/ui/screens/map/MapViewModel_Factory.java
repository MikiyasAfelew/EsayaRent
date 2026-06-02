package com.renteasy.app.ui.screens.map;

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
public final class MapViewModel_Factory implements Factory<MapViewModel> {
  private final Provider<PropertyRepository> propertyRepositoryProvider;

  public MapViewModel_Factory(Provider<PropertyRepository> propertyRepositoryProvider) {
    this.propertyRepositoryProvider = propertyRepositoryProvider;
  }

  @Override
  public MapViewModel get() {
    return newInstance(propertyRepositoryProvider.get());
  }

  public static MapViewModel_Factory create(
      Provider<PropertyRepository> propertyRepositoryProvider) {
    return new MapViewModel_Factory(propertyRepositoryProvider);
  }

  public static MapViewModel newInstance(PropertyRepository propertyRepository) {
    return new MapViewModel(propertyRepository);
  }
}
