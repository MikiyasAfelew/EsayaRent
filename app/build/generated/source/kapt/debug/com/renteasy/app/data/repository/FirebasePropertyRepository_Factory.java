package com.renteasy.app.data.repository;

import android.content.Context;
import com.google.firebase.firestore.FirebaseFirestore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class FirebasePropertyRepository_Factory implements Factory<FirebasePropertyRepository> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  private final Provider<Context> contextProvider;

  public FirebasePropertyRepository_Factory(Provider<FirebaseFirestore> firestoreProvider,
      Provider<Context> contextProvider) {
    this.firestoreProvider = firestoreProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public FirebasePropertyRepository get() {
    return newInstance(firestoreProvider.get(), contextProvider.get());
  }

  public static FirebasePropertyRepository_Factory create(
      Provider<FirebaseFirestore> firestoreProvider, Provider<Context> contextProvider) {
    return new FirebasePropertyRepository_Factory(firestoreProvider, contextProvider);
  }

  public static FirebasePropertyRepository newInstance(FirebaseFirestore firestore,
      Context context) {
    return new FirebasePropertyRepository(firestore, context);
  }
}
