package com.renteasy.app.ui.screens.owner;

import com.renteasy.app.domain.repository.AuthRepository;
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
public final class AddPropertyViewModel_Factory implements Factory<AddPropertyViewModel> {
  private final Provider<PropertyRepository> propertyRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public AddPropertyViewModel_Factory(Provider<PropertyRepository> propertyRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.propertyRepositoryProvider = propertyRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public AddPropertyViewModel get() {
    return newInstance(propertyRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static AddPropertyViewModel_Factory create(
      Provider<PropertyRepository> propertyRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new AddPropertyViewModel_Factory(propertyRepositoryProvider, authRepositoryProvider);
  }

  public static AddPropertyViewModel newInstance(PropertyRepository propertyRepository,
      AuthRepository authRepository) {
    return new AddPropertyViewModel(propertyRepository, authRepository);
  }
}
