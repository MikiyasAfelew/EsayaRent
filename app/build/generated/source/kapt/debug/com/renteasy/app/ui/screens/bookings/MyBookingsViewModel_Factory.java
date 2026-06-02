package com.renteasy.app.ui.screens.bookings;

import com.renteasy.app.domain.repository.AuthRepository;
import com.renteasy.app.domain.repository.BookingRepository;
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
public final class MyBookingsViewModel_Factory implements Factory<MyBookingsViewModel> {
  private final Provider<BookingRepository> bookingRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public MyBookingsViewModel_Factory(Provider<BookingRepository> bookingRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.bookingRepositoryProvider = bookingRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public MyBookingsViewModel get() {
    return newInstance(bookingRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static MyBookingsViewModel_Factory create(
      Provider<BookingRepository> bookingRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new MyBookingsViewModel_Factory(bookingRepositoryProvider, authRepositoryProvider);
  }

  public static MyBookingsViewModel newInstance(BookingRepository bookingRepository,
      AuthRepository authRepository) {
    return new MyBookingsViewModel(bookingRepository, authRepository);
  }
}
