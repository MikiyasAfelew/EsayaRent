package com.renteasy.app.data;

import android.content.Context;
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
public final class FavoritesManager_Factory implements Factory<FavoritesManager> {
  private final Provider<Context> contextProvider;

  public FavoritesManager_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public FavoritesManager get() {
    return newInstance(contextProvider.get());
  }

  public static FavoritesManager_Factory create(Provider<Context> contextProvider) {
    return new FavoritesManager_Factory(contextProvider);
  }

  public static FavoritesManager newInstance(Context context) {
    return new FavoritesManager(context);
  }
}
