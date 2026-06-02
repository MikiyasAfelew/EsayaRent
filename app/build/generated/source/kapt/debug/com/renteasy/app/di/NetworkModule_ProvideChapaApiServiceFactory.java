package com.renteasy.app.di;

import com.renteasy.app.data.api.ChapaApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class NetworkModule_ProvideChapaApiServiceFactory implements Factory<ChapaApiService> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideChapaApiServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public ChapaApiService get() {
    return provideChapaApiService(retrofitProvider.get());
  }

  public static NetworkModule_ProvideChapaApiServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideChapaApiServiceFactory(retrofitProvider);
  }

  public static ChapaApiService provideChapaApiService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideChapaApiService(retrofit));
  }
}
