package com.renteasy.app;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.renteasy.app.data.FavoritesManager;
import com.renteasy.app.data.api.ChapaApiService;
import com.renteasy.app.data.repository.FirebaseAuthRepository;
import com.renteasy.app.data.repository.FirebaseBookingRepository;
import com.renteasy.app.data.repository.FirebasePropertyRepository;
import com.renteasy.app.di.FirebaseModule_ProvideFirebaseAuthFactory;
import com.renteasy.app.di.FirebaseModule_ProvideFirebaseFirestoreFactory;
import com.renteasy.app.di.NetworkModule_ProvideChapaApiServiceFactory;
import com.renteasy.app.di.NetworkModule_ProvideRetrofitFactory;
import com.renteasy.app.ui.navigation.AuthViewModel;
import com.renteasy.app.ui.navigation.AuthViewModel_HiltModules;
import com.renteasy.app.ui.screens.auth.LoginViewModel;
import com.renteasy.app.ui.screens.auth.LoginViewModel_HiltModules;
import com.renteasy.app.ui.screens.auth.RegisterViewModel;
import com.renteasy.app.ui.screens.auth.RegisterViewModel_HiltModules;
import com.renteasy.app.ui.screens.bookings.MyBookingsViewModel;
import com.renteasy.app.ui.screens.bookings.MyBookingsViewModel_HiltModules;
import com.renteasy.app.ui.screens.checkout.CheckoutViewModel;
import com.renteasy.app.ui.screens.checkout.CheckoutViewModel_HiltModules;
import com.renteasy.app.ui.screens.detail.PropertyDetailViewModel;
import com.renteasy.app.ui.screens.detail.PropertyDetailViewModel_HiltModules;
import com.renteasy.app.ui.screens.home.HomeViewModel;
import com.renteasy.app.ui.screens.home.HomeViewModel_HiltModules;
import com.renteasy.app.ui.screens.map.MapViewModel;
import com.renteasy.app.ui.screens.map.MapViewModel_HiltModules;
import com.renteasy.app.ui.screens.owner.AddPropertyViewModel;
import com.renteasy.app.ui.screens.owner.AddPropertyViewModel_HiltModules;
import com.renteasy.app.ui.screens.owner.OwnerDashboardViewModel;
import com.renteasy.app.ui.screens.owner.OwnerDashboardViewModel_HiltModules;
import com.renteasy.app.ui.screens.saved.SavedHomesViewModel;
import com.renteasy.app.ui.screens.saved.SavedHomesViewModel_HiltModules;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import retrofit2.Retrofit;

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
public final class DaggerRentEasyApp_HiltComponents_SingletonC {
  private DaggerRentEasyApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public RentEasyApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements RentEasyApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public RentEasyApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements RentEasyApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public RentEasyApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements RentEasyApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public RentEasyApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements RentEasyApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public RentEasyApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements RentEasyApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public RentEasyApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements RentEasyApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public RentEasyApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements RentEasyApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public RentEasyApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends RentEasyApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends RentEasyApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends RentEasyApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends RentEasyApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
      injectMainActivity2(mainActivity);
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(ImmutableMap.<String, Boolean>builderWithExpectedSize(11).put(LazyClassKeyProvider.com_renteasy_app_ui_screens_owner_AddPropertyViewModel, AddPropertyViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_renteasy_app_ui_navigation_AuthViewModel, AuthViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_renteasy_app_ui_screens_checkout_CheckoutViewModel, CheckoutViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_renteasy_app_ui_screens_home_HomeViewModel, HomeViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_renteasy_app_ui_screens_auth_LoginViewModel, LoginViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_renteasy_app_ui_screens_map_MapViewModel, MapViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_renteasy_app_ui_screens_bookings_MyBookingsViewModel, MyBookingsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_renteasy_app_ui_screens_owner_OwnerDashboardViewModel, OwnerDashboardViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_renteasy_app_ui_screens_detail_PropertyDetailViewModel, PropertyDetailViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_renteasy_app_ui_screens_auth_RegisterViewModel, RegisterViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_renteasy_app_ui_screens_saved_SavedHomesViewModel, SavedHomesViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @CanIgnoreReturnValue
    private MainActivity injectMainActivity2(MainActivity instance) {
      MainActivity_MembersInjector.injectAuthRepository(instance, singletonCImpl.firebaseAuthRepositoryProvider.get());
      return instance;
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_renteasy_app_ui_screens_detail_PropertyDetailViewModel = "com.renteasy.app.ui.screens.detail.PropertyDetailViewModel";

      static String com_renteasy_app_ui_screens_owner_AddPropertyViewModel = "com.renteasy.app.ui.screens.owner.AddPropertyViewModel";

      static String com_renteasy_app_ui_screens_auth_LoginViewModel = "com.renteasy.app.ui.screens.auth.LoginViewModel";

      static String com_renteasy_app_ui_screens_checkout_CheckoutViewModel = "com.renteasy.app.ui.screens.checkout.CheckoutViewModel";

      static String com_renteasy_app_ui_navigation_AuthViewModel = "com.renteasy.app.ui.navigation.AuthViewModel";

      static String com_renteasy_app_ui_screens_home_HomeViewModel = "com.renteasy.app.ui.screens.home.HomeViewModel";

      static String com_renteasy_app_ui_screens_owner_OwnerDashboardViewModel = "com.renteasy.app.ui.screens.owner.OwnerDashboardViewModel";

      static String com_renteasy_app_ui_screens_saved_SavedHomesViewModel = "com.renteasy.app.ui.screens.saved.SavedHomesViewModel";

      static String com_renteasy_app_ui_screens_bookings_MyBookingsViewModel = "com.renteasy.app.ui.screens.bookings.MyBookingsViewModel";

      static String com_renteasy_app_ui_screens_map_MapViewModel = "com.renteasy.app.ui.screens.map.MapViewModel";

      static String com_renteasy_app_ui_screens_auth_RegisterViewModel = "com.renteasy.app.ui.screens.auth.RegisterViewModel";

      @KeepFieldType
      PropertyDetailViewModel com_renteasy_app_ui_screens_detail_PropertyDetailViewModel2;

      @KeepFieldType
      AddPropertyViewModel com_renteasy_app_ui_screens_owner_AddPropertyViewModel2;

      @KeepFieldType
      LoginViewModel com_renteasy_app_ui_screens_auth_LoginViewModel2;

      @KeepFieldType
      CheckoutViewModel com_renteasy_app_ui_screens_checkout_CheckoutViewModel2;

      @KeepFieldType
      AuthViewModel com_renteasy_app_ui_navigation_AuthViewModel2;

      @KeepFieldType
      HomeViewModel com_renteasy_app_ui_screens_home_HomeViewModel2;

      @KeepFieldType
      OwnerDashboardViewModel com_renteasy_app_ui_screens_owner_OwnerDashboardViewModel2;

      @KeepFieldType
      SavedHomesViewModel com_renteasy_app_ui_screens_saved_SavedHomesViewModel2;

      @KeepFieldType
      MyBookingsViewModel com_renteasy_app_ui_screens_bookings_MyBookingsViewModel2;

      @KeepFieldType
      MapViewModel com_renteasy_app_ui_screens_map_MapViewModel2;

      @KeepFieldType
      RegisterViewModel com_renteasy_app_ui_screens_auth_RegisterViewModel2;
    }
  }

  private static final class ViewModelCImpl extends RentEasyApp_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AddPropertyViewModel> addPropertyViewModelProvider;

    private Provider<AuthViewModel> authViewModelProvider;

    private Provider<CheckoutViewModel> checkoutViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<LoginViewModel> loginViewModelProvider;

    private Provider<MapViewModel> mapViewModelProvider;

    private Provider<MyBookingsViewModel> myBookingsViewModelProvider;

    private Provider<OwnerDashboardViewModel> ownerDashboardViewModelProvider;

    private Provider<PropertyDetailViewModel> propertyDetailViewModelProvider;

    private Provider<RegisterViewModel> registerViewModelProvider;

    private Provider<SavedHomesViewModel> savedHomesViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.addPropertyViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.authViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.checkoutViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.loginViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.mapViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.myBookingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.ownerDashboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.propertyDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.registerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.savedHomesViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(ImmutableMap.<String, javax.inject.Provider<ViewModel>>builderWithExpectedSize(11).put(LazyClassKeyProvider.com_renteasy_app_ui_screens_owner_AddPropertyViewModel, ((Provider) addPropertyViewModelProvider)).put(LazyClassKeyProvider.com_renteasy_app_ui_navigation_AuthViewModel, ((Provider) authViewModelProvider)).put(LazyClassKeyProvider.com_renteasy_app_ui_screens_checkout_CheckoutViewModel, ((Provider) checkoutViewModelProvider)).put(LazyClassKeyProvider.com_renteasy_app_ui_screens_home_HomeViewModel, ((Provider) homeViewModelProvider)).put(LazyClassKeyProvider.com_renteasy_app_ui_screens_auth_LoginViewModel, ((Provider) loginViewModelProvider)).put(LazyClassKeyProvider.com_renteasy_app_ui_screens_map_MapViewModel, ((Provider) mapViewModelProvider)).put(LazyClassKeyProvider.com_renteasy_app_ui_screens_bookings_MyBookingsViewModel, ((Provider) myBookingsViewModelProvider)).put(LazyClassKeyProvider.com_renteasy_app_ui_screens_owner_OwnerDashboardViewModel, ((Provider) ownerDashboardViewModelProvider)).put(LazyClassKeyProvider.com_renteasy_app_ui_screens_detail_PropertyDetailViewModel, ((Provider) propertyDetailViewModelProvider)).put(LazyClassKeyProvider.com_renteasy_app_ui_screens_auth_RegisterViewModel, ((Provider) registerViewModelProvider)).put(LazyClassKeyProvider.com_renteasy_app_ui_screens_saved_SavedHomesViewModel, ((Provider) savedHomesViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return ImmutableMap.<Class<?>, Object>of();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_renteasy_app_ui_screens_auth_RegisterViewModel = "com.renteasy.app.ui.screens.auth.RegisterViewModel";

      static String com_renteasy_app_ui_screens_owner_OwnerDashboardViewModel = "com.renteasy.app.ui.screens.owner.OwnerDashboardViewModel";

      static String com_renteasy_app_ui_navigation_AuthViewModel = "com.renteasy.app.ui.navigation.AuthViewModel";

      static String com_renteasy_app_ui_screens_checkout_CheckoutViewModel = "com.renteasy.app.ui.screens.checkout.CheckoutViewModel";

      static String com_renteasy_app_ui_screens_auth_LoginViewModel = "com.renteasy.app.ui.screens.auth.LoginViewModel";

      static String com_renteasy_app_ui_screens_detail_PropertyDetailViewModel = "com.renteasy.app.ui.screens.detail.PropertyDetailViewModel";

      static String com_renteasy_app_ui_screens_saved_SavedHomesViewModel = "com.renteasy.app.ui.screens.saved.SavedHomesViewModel";

      static String com_renteasy_app_ui_screens_home_HomeViewModel = "com.renteasy.app.ui.screens.home.HomeViewModel";

      static String com_renteasy_app_ui_screens_map_MapViewModel = "com.renteasy.app.ui.screens.map.MapViewModel";

      static String com_renteasy_app_ui_screens_owner_AddPropertyViewModel = "com.renteasy.app.ui.screens.owner.AddPropertyViewModel";

      static String com_renteasy_app_ui_screens_bookings_MyBookingsViewModel = "com.renteasy.app.ui.screens.bookings.MyBookingsViewModel";

      @KeepFieldType
      RegisterViewModel com_renteasy_app_ui_screens_auth_RegisterViewModel2;

      @KeepFieldType
      OwnerDashboardViewModel com_renteasy_app_ui_screens_owner_OwnerDashboardViewModel2;

      @KeepFieldType
      AuthViewModel com_renteasy_app_ui_navigation_AuthViewModel2;

      @KeepFieldType
      CheckoutViewModel com_renteasy_app_ui_screens_checkout_CheckoutViewModel2;

      @KeepFieldType
      LoginViewModel com_renteasy_app_ui_screens_auth_LoginViewModel2;

      @KeepFieldType
      PropertyDetailViewModel com_renteasy_app_ui_screens_detail_PropertyDetailViewModel2;

      @KeepFieldType
      SavedHomesViewModel com_renteasy_app_ui_screens_saved_SavedHomesViewModel2;

      @KeepFieldType
      HomeViewModel com_renteasy_app_ui_screens_home_HomeViewModel2;

      @KeepFieldType
      MapViewModel com_renteasy_app_ui_screens_map_MapViewModel2;

      @KeepFieldType
      AddPropertyViewModel com_renteasy_app_ui_screens_owner_AddPropertyViewModel2;

      @KeepFieldType
      MyBookingsViewModel com_renteasy_app_ui_screens_bookings_MyBookingsViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.renteasy.app.ui.screens.owner.AddPropertyViewModel 
          return (T) new AddPropertyViewModel(singletonCImpl.firebasePropertyRepositoryProvider.get(), singletonCImpl.firebaseAuthRepositoryProvider.get());

          case 1: // com.renteasy.app.ui.navigation.AuthViewModel 
          return (T) new AuthViewModel(singletonCImpl.firebaseAuthRepositoryProvider.get());

          case 2: // com.renteasy.app.ui.screens.checkout.CheckoutViewModel 
          return (T) new CheckoutViewModel(singletonCImpl.firebasePropertyRepositoryProvider.get(), singletonCImpl.firebaseBookingRepositoryProvider.get(), singletonCImpl.firebaseAuthRepositoryProvider.get(), singletonCImpl.provideChapaApiServiceProvider.get());

          case 3: // com.renteasy.app.ui.screens.home.HomeViewModel 
          return (T) new HomeViewModel(singletonCImpl.firebasePropertyRepositoryProvider.get());

          case 4: // com.renteasy.app.ui.screens.auth.LoginViewModel 
          return (T) new LoginViewModel(singletonCImpl.firebaseAuthRepositoryProvider.get());

          case 5: // com.renteasy.app.ui.screens.map.MapViewModel 
          return (T) new MapViewModel(singletonCImpl.firebasePropertyRepositoryProvider.get());

          case 6: // com.renteasy.app.ui.screens.bookings.MyBookingsViewModel 
          return (T) new MyBookingsViewModel(singletonCImpl.firebaseBookingRepositoryProvider.get(), singletonCImpl.firebaseAuthRepositoryProvider.get());

          case 7: // com.renteasy.app.ui.screens.owner.OwnerDashboardViewModel 
          return (T) new OwnerDashboardViewModel(singletonCImpl.firebasePropertyRepositoryProvider.get(), singletonCImpl.firebaseBookingRepositoryProvider.get(), singletonCImpl.firebaseAuthRepositoryProvider.get());

          case 8: // com.renteasy.app.ui.screens.detail.PropertyDetailViewModel 
          return (T) new PropertyDetailViewModel(singletonCImpl.firebasePropertyRepositoryProvider.get(), singletonCImpl.favoritesManagerProvider.get());

          case 9: // com.renteasy.app.ui.screens.auth.RegisterViewModel 
          return (T) new RegisterViewModel(singletonCImpl.firebaseAuthRepositoryProvider.get());

          case 10: // com.renteasy.app.ui.screens.saved.SavedHomesViewModel 
          return (T) new SavedHomesViewModel(singletonCImpl.firebasePropertyRepositoryProvider.get(), singletonCImpl.favoritesManagerProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends RentEasyApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends RentEasyApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends RentEasyApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<FirebaseAuth> provideFirebaseAuthProvider;

    private Provider<FirebaseFirestore> provideFirebaseFirestoreProvider;

    private Provider<FirebaseAuthRepository> firebaseAuthRepositoryProvider;

    private Provider<FirebasePropertyRepository> firebasePropertyRepositoryProvider;

    private Provider<FirebaseBookingRepository> firebaseBookingRepositoryProvider;

    private Provider<Retrofit> provideRetrofitProvider;

    private Provider<ChapaApiService> provideChapaApiServiceProvider;

    private Provider<FavoritesManager> favoritesManagerProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideFirebaseAuthProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseAuth>(singletonCImpl, 1));
      this.provideFirebaseFirestoreProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseFirestore>(singletonCImpl, 2));
      this.firebaseAuthRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseAuthRepository>(singletonCImpl, 0));
      this.firebasePropertyRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<FirebasePropertyRepository>(singletonCImpl, 3));
      this.firebaseBookingRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseBookingRepository>(singletonCImpl, 4));
      this.provideRetrofitProvider = DoubleCheck.provider(new SwitchingProvider<Retrofit>(singletonCImpl, 6));
      this.provideChapaApiServiceProvider = DoubleCheck.provider(new SwitchingProvider<ChapaApiService>(singletonCImpl, 5));
      this.favoritesManagerProvider = DoubleCheck.provider(new SwitchingProvider<FavoritesManager>(singletonCImpl, 7));
    }

    @Override
    public void injectRentEasyApp(RentEasyApp rentEasyApp) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.renteasy.app.data.repository.FirebaseAuthRepository 
          return (T) new FirebaseAuthRepository(singletonCImpl.provideFirebaseAuthProvider.get(), singletonCImpl.provideFirebaseFirestoreProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 1: // com.google.firebase.auth.FirebaseAuth 
          return (T) FirebaseModule_ProvideFirebaseAuthFactory.provideFirebaseAuth();

          case 2: // com.google.firebase.firestore.FirebaseFirestore 
          return (T) FirebaseModule_ProvideFirebaseFirestoreFactory.provideFirebaseFirestore();

          case 3: // com.renteasy.app.data.repository.FirebasePropertyRepository 
          return (T) new FirebasePropertyRepository(singletonCImpl.provideFirebaseFirestoreProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 4: // com.renteasy.app.data.repository.FirebaseBookingRepository 
          return (T) new FirebaseBookingRepository(singletonCImpl.provideFirebaseFirestoreProvider.get());

          case 5: // com.renteasy.app.data.api.ChapaApiService 
          return (T) NetworkModule_ProvideChapaApiServiceFactory.provideChapaApiService(singletonCImpl.provideRetrofitProvider.get());

          case 6: // retrofit2.Retrofit 
          return (T) NetworkModule_ProvideRetrofitFactory.provideRetrofit();

          case 7: // com.renteasy.app.data.FavoritesManager 
          return (T) new FavoritesManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
