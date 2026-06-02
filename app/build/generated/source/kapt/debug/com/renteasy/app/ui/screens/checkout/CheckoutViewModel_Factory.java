package com.renteasy.app.ui.screens.checkout;

import com.renteasy.app.data.api.ChapaApiService;
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
public final class CheckoutViewModel_Factory implements Factory<CheckoutViewModel> {
  private final Provider<PropertyRepository> propertyRepositoryProvider;

  private final Provider<BookingRepository> bookingRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<ChapaApiService> chapaApiServiceProvider;

  public CheckoutViewModel_Factory(Provider<PropertyRepository> propertyRepositoryProvider,
      Provider<BookingRepository> bookingRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<ChapaApiService> chapaApiServiceProvider) {
    this.propertyRepositoryProvider = propertyRepositoryProvider;
    this.bookingRepositoryProvider = bookingRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
    this.chapaApiServiceProvider = chapaApiServiceProvider;
  }

  @Override
  public CheckoutViewModel get() {
    return newInstance(propertyRepositoryProvider.get(), bookingRepositoryProvider.get(), authRepositoryProvider.get(), chapaApiServiceProvider.get());
  }

  public static CheckoutViewModel_Factory create(
      Provider<PropertyRepository> propertyRepositoryProvider,
      Provider<BookingRepository> bookingRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<ChapaApiService> chapaApiServiceProvider) {
    return new CheckoutViewModel_Factory(propertyRepositoryProvider, bookingRepositoryProvider, authRepositoryProvider, chapaApiServiceProvider);
  }

  public static CheckoutViewModel newInstance(PropertyRepository propertyRepository,
      BookingRepository bookingRepository, AuthRepository authRepository,
      ChapaApiService chapaApiService) {
    return new CheckoutViewModel(propertyRepository, bookingRepository, authRepository, chapaApiService);
  }
}
