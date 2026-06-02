package com.renteasy.app.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class FirebaseBookingRepository_Factory implements Factory<FirebaseBookingRepository> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  public FirebaseBookingRepository_Factory(Provider<FirebaseFirestore> firestoreProvider) {
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public FirebaseBookingRepository get() {
    return newInstance(firestoreProvider.get());
  }

  public static FirebaseBookingRepository_Factory create(
      Provider<FirebaseFirestore> firestoreProvider) {
    return new FirebaseBookingRepository_Factory(firestoreProvider);
  }

  public static FirebaseBookingRepository newInstance(FirebaseFirestore firestore) {
    return new FirebaseBookingRepository(firestore);
  }
}
