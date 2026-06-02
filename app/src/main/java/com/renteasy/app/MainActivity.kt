package com.renteasy.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.renteasy.app.domain.repository.AuthRepository
import com.renteasy.app.ui.components.RentEasyBottomNav
import com.renteasy.app.ui.navigation.RentEasyNavGraph
import com.renteasy.app.ui.navigation.Screen
import com.renteasy.app.ui.theme.ThemeController
import androidx.compose.material3.MaterialTheme
import com.renteasy.app.ui.theme.NightBackground
import com.renteasy.app.ui.theme.RentEasyTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject




@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isSystemDark = (resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK) == android.content.res.Configuration.UI_MODE_NIGHT_YES
        ThemeController.initialize(isSystemDark)
        
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val isDarkTheme by ThemeController.isDarkMode.collectAsState()
            RentEasyTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route
                
                val currentUser by authRepository.currentUser.collectAsState(initial = null)
                val isOwner = currentUser?.isOwner ?: false

                val showBottomNav = currentRoute != null && currentRoute !in setOf(
                    "welcome",
                    "login",
                    "register",
                    "splash",
                    "property_detail/{propertyId}",
                    "checkout/{propertyId}",
                    "identity_verification",
                    "verification_status",
                    "edit_profile",
                    "payment_methods",
                    "saved_homes",
                    "price_alerts",
                    "about_support"
                )

                Scaffold(
                    containerColor = MaterialTheme.colorScheme.background,
                    bottomBar = {
                        if (showBottomNav) {
                            RentEasyBottomNav(
                                currentRoute = currentRoute,
                                isOwner = isOwner,
                                onNavigate = { route ->
                                    navController.navigate(route) {
                                        // Handle different start destinations based on role
                                        val startDest = if (isOwner) Screen.OwnerDashboard.route else Screen.Home.route
                                        popUpTo(startDest) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = if (showBottomNav) innerPadding.calculateBottomPadding() else 0.dp)
                    ) {
                        RentEasyNavGraph(navController = navController)
                    }
                }
            }
        }
    }
}
