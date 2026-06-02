package com.renteasy.app.ui.screens.owner;

import com.renteasy.app.domain.repository.AuthRepository;
import com.renteasy.app.domain.repository.BookingRepository;
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
public final class OwnerDashboardViewModel_Factory implements Factory<OwnerDashboardViewModel> {
  private final Provider<PropertyRepository> propertyRepositoryProvider;

  private final Provider<BookingRepository> bookingRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public OwnerDashboardViewModel_Factory(Provider<PropertyRepository> propertyRepositoryProvider,
      Provider<BookingRepository> bookingRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.propertyRepositoryProvider = propertyRepositoryProvider;
    this.bookingRepositoryProvider = bookingRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public OwnerDashboardViewModel get() {
    return newInstance(propertyRepositoryProvider.get(), bookingRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static OwnerDashboardViewModel_Factory create(
      Provider<PropertyRepository> propertyRepositoryProvider,
      Provider<BookingRepository> bookingRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new OwnerDashboardViewModel_Factory(propertyRepositoryProvider, bookingRepositoryProvider, authRepositoryProvider);
  }

  public static OwnerDashboardViewModel newInstance(PropertyRepository propertyRepository,
      BookingRepository bookingRepository, AuthRepository authRepository) {
    return new OwnerDashboardViewModel(propertyRepository, bookingRepository, authRepository);
  }
}
