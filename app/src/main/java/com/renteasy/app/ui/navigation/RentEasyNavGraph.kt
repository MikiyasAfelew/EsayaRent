package com.renteasy.app.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.background
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.renteasy.app.domain.repository.AuthRepository
import com.renteasy.app.ui.screens.bookings.MyBookingsScreen
import com.renteasy.app.ui.screens.checkout.CheckoutScreen
import com.renteasy.app.ui.screens.detail.PropertyDetailScreen
import com.renteasy.app.ui.screens.home.HomeScreen
import com.renteasy.app.ui.screens.owner.OwnerDashboardScreen
import com.renteasy.app.ui.screens.owner.AddPropertyScreen
import com.renteasy.app.ui.screens.owner.MyListingsScreen
import com.renteasy.app.ui.screens.verification.IdentityVerificationScreen
import com.renteasy.app.ui.screens.verification.VerificationStatusScreen
import com.renteasy.app.ui.screens.chat.ChatScreen
import com.renteasy.app.ui.screens.auth.LoginScreen
import com.renteasy.app.ui.screens.auth.CreateAccountScreen
import com.renteasy.app.ui.screens.welcome.WelcomeScreen
import com.renteasy.app.ui.screens.profile.EditProfileScreen
import com.renteasy.app.ui.screens.profile.PaymentMethodsScreen
import com.renteasy.app.ui.screens.PlaceholderScreen
import com.renteasy.app.ui.screens.saved.SavedHomesScreen
import com.renteasy.app.ui.screens.alerts.PriceAlertsScreen
import kotlinx.coroutines.launch

@Composable
fun RentEasyNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            // Smart router: wait for Firebase auth state then redirect
            val user by authViewModel.currentUser.collectAsState(initial = null)
            var timedOut by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                if (authViewModel.hasUserSession) {
                    // Only wait for timeout if there is an active session we are trying to load
                    kotlinx.coroutines.delay(3000)
                    timedOut = true
                } else {
                    // No session exists, go to Welcome immediately
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            }

            LaunchedEffect(user, timedOut) {
                if (authViewModel.hasUserSession) {
                    when {
                        user != null -> {
                            val target = if (user!!.isOwner) Screen.OwnerDashboard.route else Screen.Home.route
                            navController.navigate(target) {
                                popUpTo(Screen.Splash.route) { inclusive = true }
                            }
                        }
                        timedOut -> {
                            // Stale/broken session or network timeout, send to Welcome
                            navController.navigate(Screen.Welcome.route) {
                                popUpTo(Screen.Splash.route) { inclusive = true }
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }

        // --- Welcome Screen ---
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onGetStarted = { navController.navigate(Screen.Login.route) }
            )
        }

        // --- Auth Flow ---
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    val user = authViewModel.currentUser.value
                    val target = if (user?.isOwner == true) Screen.OwnerDashboard.route else Screen.Home.route
                    navController.navigate(target) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onRegister = { navController.navigate(Screen.Register.route) },
                onBrowseGuest = { 
                    scope.launch {
                        authViewModel.signOut()
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                    }
                }
            )
        }
        
        composable(Screen.Register.route) {
            CreateAccountScreen(
                onBack = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.popBackStack()
                }
            )
        }

        // --- Renter Flow ---
        composable(Screen.Home.route) {
            HomeScreen(
                onPropertyClick = { id ->
                    navController.navigate(Screen.PropertyDetail.createRoute(id))
                },
                onMapClick = { navController.navigate(Screen.Map.route) }
            )
        }
        
        composable(Screen.Map.route) {
            com.renteasy.app.ui.screens.map.MapScreen(onPropertyClick = { id ->
                navController.navigate(Screen.PropertyDetail.createRoute(id))
            })
        }
        
        // --- Owner Flow ---
        composable(Screen.OwnerDashboard.route) {
            OwnerDashboardScreen(
                onAddListing = { navController.navigate(Screen.AddProperty.route) },
                onNavigateToVerification = {
                    val user = authViewModel.currentUser.value
                    if (user != null && user.verificationLevel != com.renteasy.app.domain.model.UserVerificationLevel.BASIC) {
                        navController.navigate(Screen.VerificationStatus.route)
                    } else {
                        navController.navigate(Screen.IdentityVerification.route)
                    }
                },
                onNavigateToMyListings = {
                    navController.navigate(Screen.MyListings.route)
                }
            )
        }
        
        composable(Screen.AddProperty.route) {
            val userState by authViewModel.currentUser.collectAsState()
            val isVerified = userState?.verificationLevel == com.renteasy.app.domain.model.UserVerificationLevel.FULLY_VERIFIED ||
                    userState?.verificationLevel == com.renteasy.app.domain.model.UserVerificationLevel.ID_VERIFIED

            if (userState == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (isVerified) {
                AddPropertyScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            } else {
                LaunchedEffect(userState) {
                    if (userState!!.verificationLevel != com.renteasy.app.domain.model.UserVerificationLevel.BASIC) {
                        navController.navigate(Screen.VerificationStatus.route) {
                            popUpTo(Screen.OwnerDashboard.route) { inclusive = false }
                        }
                    } else {
                        navController.navigate(Screen.IdentityVerification.route) {
                            popUpTo(Screen.OwnerDashboard.route) { inclusive = false }
                        }
                    }
                }
            }
        }
        composable(Screen.MyListings.route) {
            MyListingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onAddProperty = { navController.navigate(Screen.AddProperty.route) }
            )
        }

        // --- Shared Flow ---
        composable(Screen.Bookings.route) {
            MyBookingsScreen(
                onSignInRequired = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onContactOwner = { ownerId, ownerName, title, price, image ->
                    navController.navigate(Screen.Chat.createRoute(ownerId, ownerName, title, price, image))
                }
            )
        }
        
        composable(Screen.Profile.route) {
            com.renteasy.app.ui.screens.profile.ProfileScreen(
                onLogout = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onAddProperty = {
                    navController.navigate(Screen.AddProperty.route)
                },
                onNavigateToMyListings = {
                    navController.navigate(Screen.MyListings.route)
                },
                onNavigateToVerification = {
                    val user = authViewModel.currentUser.value
                    if (user != null && user.verificationLevel != com.renteasy.app.domain.model.UserVerificationLevel.BASIC) {
                        navController.navigate(Screen.VerificationStatus.route)
                    } else {
                        navController.navigate(Screen.IdentityVerification.route)
                    }
                },
                onNavigateToEditProfile = {
                    navController.navigate(Screen.EditProfile.route)
                },
                onNavigateToSavedHomes = {
                    navController.navigate(Screen.SavedHomes.route)
                },
                onNavigateToPriceAlerts = {
                    navController.navigate(Screen.PriceAlerts.route)
                },
                onNavigateToBookings = {
                    navController.navigate(Screen.Bookings.route)
                },
                onNavigateToAboutSupport = {
                    navController.navigate(Screen.AboutSupport.route)
                }
            )
        }

        composable(Screen.EditProfile.route) {
            EditProfileScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.PaymentMethods.route) {
            PaymentMethodsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.PropertyDetail.route,
            arguments = listOf(navArgument("propertyId") { type = NavType.StringType })
        ) { backStackEntry ->
            val propertyId = backStackEntry.arguments?.getString("propertyId") ?: ""
            PropertyDetailScreen(
                propertyId = propertyId,
                onBack = { navController.popBackStack() },
                onBookNow = { navController.navigate(Screen.Checkout.createRoute(propertyId)) },
                onMessageClick = { ownerId, ownerName, title, price, image ->
                    navController.navigate(Screen.Chat.createRoute(ownerId, ownerName, title, price, image))
                },
                onNavigateToVerification = {
                    val user = authViewModel.currentUser.value
                    if (user != null && user.verificationLevel != com.renteasy.app.domain.model.UserVerificationLevel.BASIC) {
                        navController.navigate(Screen.VerificationStatus.route)
                    } else {
                        navController.navigate(Screen.IdentityVerification.route)
                    }
                },
                onSignInRequired = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.Chat.route,
            arguments = listOf(
                navArgument("ownerId") { type = NavType.StringType },
                navArgument("ownerName") {
                    type = NavType.StringType
                    defaultValue = "Support"
                    nullable = true
                },
                navArgument("propertyTitle") {
                    type = NavType.StringType
                    defaultValue = "RentEasy Support"
                    nullable = true
                },
                navArgument("propertyPrice") {
                    type = NavType.StringType
                    defaultValue = "-"
                    nullable = true
                },
                navArgument("propertyImage") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val ownerId = backStackEntry.arguments?.getString("ownerId") ?: ""
            val ownerName = java.net.URLDecoder.decode(backStackEntry.arguments?.getString("ownerName") ?: "Support", "UTF-8")
            val propertyTitle = java.net.URLDecoder.decode(backStackEntry.arguments?.getString("propertyTitle") ?: "RentEasy Support", "UTF-8")
            val propertyPrice = java.net.URLDecoder.decode(backStackEntry.arguments?.getString("propertyPrice") ?: "-", "UTF-8")
            val propertyImage = java.net.URLDecoder.decode(backStackEntry.arguments?.getString("propertyImage") ?: "", "UTF-8")
            ChatScreen(
                ownerId = ownerId,
                ownerName = ownerName,
                propertyTitle = propertyTitle,
                propertyPrice = propertyPrice,
                propertyImage = propertyImage,
                onBack = { navController.popBackStack() },
                onNavigateToChat = { targetId, targetName, title, price, image ->
                    navController.navigate(Screen.Chat.createRoute(targetId, targetName, title, price, image))
                }
            )
        }

        composable(Screen.IdentityVerification.route) {
            IdentityVerificationScreen(
                onBack = { navController.popBackStack() },
                onVerificationSubmitted = {
                    navController.navigate(Screen.VerificationStatus.route) {
                        popUpTo(Screen.IdentityVerification.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.VerificationStatus.route) {
            VerificationStatusScreen(
                onBack = { navController.popBackStack() },
                onContactSupport = { /* Action */ }
            )
        }

        composable(
            route = Screen.Checkout.route,
            arguments = listOf(navArgument("propertyId") { type = NavType.StringType })
        ) { backStackEntry ->
            val propertyId = backStackEntry.arguments?.getString("propertyId") ?: ""
            CheckoutScreen(
                propertyId = propertyId,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.SavedHomes.route) {
            SavedHomesScreen(
                onNavigateBack = { navController.popBackStack() },
                onPropertyClick = { propertyId ->
                    navController.navigate(Screen.PropertyDetail.createRoute(propertyId))
                }
            )
        }

        composable(Screen.PriceAlerts.route) {
            PriceAlertsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AboutSupport.route) {
            com.renteasy.app.ui.screens.profile.AboutSupportScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
