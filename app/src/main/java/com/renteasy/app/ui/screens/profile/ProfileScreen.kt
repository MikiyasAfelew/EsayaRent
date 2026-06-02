package com.renteasy.app.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Login
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.automirrored.outlined.ContactSupport
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.renteasy.app.domain.model.User
import com.renteasy.app.domain.model.UserVerificationLevel
import com.renteasy.app.ui.navigation.AuthViewModel
import com.renteasy.app.ui.theme.*
import com.renteasy.app.ui.theme.ThemeController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    onAddProperty: () -> Unit = {},
    onNavigateToMyListings: () -> Unit = {},
    onNavigateToVerification: () -> Unit = {},
    onNavigateToEditProfile: () -> Unit = {},
    onNavigateToSavedHomes: () -> Unit = {},
    onNavigateToPriceAlerts: () -> Unit = {},
    onNavigateToBookings: () -> Unit = {},
    onNavigateToAboutSupport: () -> Unit = {},
    viewModel: AuthViewModel = hiltViewModel(),
    bookingsViewModel: com.renteasy.app.ui.screens.bookings.MyBookingsViewModel = hiltViewModel(),
    ownerViewModel: com.renteasy.app.ui.screens.owner.OwnerDashboardViewModel = hiltViewModel()
) {
    val currentUser by viewModel.currentUser.collectAsState(initial = null)
    var showSignInPrompt by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val isOwner = currentUser?.isOwner == true
    val myBookings by bookingsViewModel.renterBookings.collectAsState()
    val ownerEarnings by ownerViewModel.totalEarnings.collectAsState()
    val ownerOccupancy by ownerViewModel.occupancyRate.collectAsState()

    // Compute the image model: ByteArray for base64 avatars, String for https URLs, null for letter fallback
    // Explicitly typed as Any? so Kotlin uses Any.equals() at the null-check site
    val avatarImageModel: Any? = remember(currentUser?.avatarUrl) {
        val url = currentUser?.avatarUrl
        when {
            url.isNullOrBlank() -> null           // show letter avatar
            url.startsWith("data:image") -> {     // base64 encoded — convert to ByteArray for Coil
                try {
                    val base64Part = url.substringAfter("base64,")
                    android.util.Base64.decode(base64Part, android.util.Base64.DEFAULT)
                } catch (e: Exception) {
                    null                           // decode failed — fall back to letter avatar
                }
            }
            else -> url                            // normal https:// URL
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Profile", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Profile Header ---
            Spacer(Modifier.height(24.dp))
            Box(contentAlignment = Alignment.BottomEnd) {
                Surface(
                    modifier = Modifier.size(110.dp),
                    shape = CircleShape,
                    border = androidx.compose.foundation.BorderStroke(
                        3.dp,
                        if (currentUser == null) MaterialTheme.colorScheme.outline
                        else MaterialTheme.colorScheme.primary
                    ),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        // Always render the letter initial as a base layer
                        Text(
                            text = (currentUser?.fullName ?: "G").take(1).uppercase(),
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        // If we have a real image URL or ByteArray, overlay it on top
                        if (avatarImageModel != null) {
                            androidx.compose.foundation.layout.Box(
                                modifier = androidx.compose.ui.Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                            ) {
                                AsyncImage(
                                    model = androidx.compose.ui.platform.LocalContext.current.let { ctx ->
                                        coil.request.ImageRequest.Builder(ctx)
                                            .data(avatarImageModel)
                                            .crossfade(true)
                                            .build()
                                    },
                                    contentDescription = currentUser?.fullName,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
                val isUserVerified = currentUser?.verificationLevel == UserVerificationLevel.ID_VERIFIED || currentUser?.verificationLevel == UserVerificationLevel.FULLY_VERIFIED
                if (isUserVerified) {
                    Surface(
                        modifier = Modifier.size(32.dp),
                        color = VerifiedGreen,
                        shape = CircleShape,
                        border = androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.background)
                    ) {
                        Icon(Icons.Rounded.Check, null, tint = Color.White, modifier = Modifier.padding(6.dp))
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Text(
                text = currentUser?.fullName ?: "Guest Explorer",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = currentUser?.email ?: "Sign in to access your profile",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(Modifier.height(12.dp))
            Surface(
                color = if (currentUser == null) MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (currentUser == null) "GUEST SESSION" 
                           else if (isOwner) {
                               if (currentUser?.verificationLevel == UserVerificationLevel.ID_VERIFIED || currentUser?.verificationLevel == UserVerificationLevel.FULLY_VERIFIED) "VERIFIED OWNER"
                               else "PRO OWNER"
                           } else {
                               if (currentUser?.verificationLevel == UserVerificationLevel.ID_VERIFIED || currentUser?.verificationLevel == UserVerificationLevel.FULLY_VERIFIED) "VERIFIED RENTER"
                               else "BASIC RENTER"
                           },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    color = if (currentUser == null) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Black
                )
            }

            // --- Stats Row (Differentiated) ---
            if (currentUser != null) {
                Spacer(Modifier.height(32.dp))
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    if (isOwner) {
                        StatBox(Modifier.weight(1f), "%,d ETB".format(ownerEarnings), "Net Earnings")
                        StatBox(Modifier.weight(1f), "$ownerOccupancy%", "Occupancy")
                    } else {
                        StatBox(Modifier.weight(1f), myBookings.size.toString(), "Bookings")
                        StatBox(Modifier.weight(1f), "0", "Saved") // TBD
                    }
                }
            }

            // --- Dashboard Sections ---
            Spacer(Modifier.height(32.dp))
            
            // Role-based sections
            if (isOwner) {
                ProfileMenuSection(title = "Owner Control Panel") {
                    MenuRow(Icons.Outlined.AddBusiness, "Add New Property", onClick = onAddProperty)
                    MenuRow(Icons.Outlined.HomeWork, "My Listings", onClick = onNavigateToMyListings)
                    MenuRow(Icons.Outlined.Analytics, "Earnings & Insights")
                    MenuRow(Icons.AutoMirrored.Outlined.ContactSupport, "Owner Support", onClick = onNavigateToAboutSupport)
                }
            } else {
                ProfileMenuSection(title = "Renter Activity") {
                    MenuRow(
                        Icons.Outlined.FavoriteBorder,
                        "Saved Homes",
                        onClick = {
                            if (currentUser == null) showSignInPrompt = true
                            else onNavigateToSavedHomes()
                        },
                        badge = if (currentUser == null) { { Icon(Icons.Rounded.Lock, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp)) } } else null
                    )
                    MenuRow(
                        Icons.Outlined.History,
                        "Rental History",
                        onClick = {
                            if (currentUser == null) showSignInPrompt = true
                            else onNavigateToBookings()
                        },
                        badge = if (currentUser == null) { { Icon(Icons.Rounded.Lock, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp)) } } else null
                    )
                    MenuRow(
                        Icons.Outlined.NotificationsNone,
                        "Price Alerts",
                        onClick = {
                            if (currentUser == null) showSignInPrompt = true
                            else onNavigateToPriceAlerts()
                        },
                        badge = if (currentUser == null) { { Icon(Icons.Rounded.Lock, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp)) } } else null
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            ProfileMenuSection(title = "Account Management") {
                MenuRow(
                    icon = Icons.Outlined.Person,
                    label = "Edit Profile",
                    onClick = {
                        if (currentUser == null) showSignInPrompt = true
                        else onNavigateToEditProfile()
                    },
                    badge = if (currentUser == null) { { Icon(Icons.Rounded.Lock, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp)) } } else null
                )
                if (currentUser?.isAdmin == true) {
                    MenuRow(Icons.Outlined.AdminPanelSettings, "Admin Panel (Web only)")
                }
                MenuRow(
                    icon = Icons.Outlined.VerifiedUser,
                    label = "Identity Verification",
                    onClick = {
                        if (currentUser == null) showSignInPrompt = true
                        else onNavigateToVerification()
                    },
                    badge = if (currentUser == null) {
                        { Icon(Icons.Rounded.Lock, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp)) }
                    } else {
                        {
                            val color = when (currentUser?.verificationLevel) {
                                UserVerificationLevel.FULLY_VERIFIED -> VerifiedGreen
                                UserVerificationLevel.ID_VERIFIED -> MaterialTheme.colorScheme.primary
                                else -> MaterialTheme.colorScheme.onSurfaceVariant
                            }
                            Text(
                                currentUser?.verificationLevel?.name ?: "BASIC",
                                color = color,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                )
                MenuRow(
                    icon = Icons.AutoMirrored.Outlined.ContactSupport,
                    label = "About Us & Support",
                    onClick = onNavigateToAboutSupport
                )
            }

            Spacer(Modifier.height(16.dp))

            ProfileMenuSection(title = "Preferences") {
                val isDarkTheme by ThemeController.isDarkMode.collectAsState()
                MenuRow(Icons.Outlined.Palette, "Theme", onClick = { ThemeController.toggle() }) {
                    Text(if (isDarkTheme) "Dark" else "Light", color = MaterialTheme.colorScheme.primary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            // --- Log Out / Sign In ---
            Spacer(Modifier.height(40.dp))
            if (currentUser == null) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .clickable {
                            onLogout()
                        },
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.AutoMirrored.Rounded.Login, null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.width(12.dp))
                        Text("Sign In or Register", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                }
            } else {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .clickable { 
                            scope.launch {
                                viewModel.signOut()
                                onLogout()
                            }
                        },
                    color = Color(0xFFEF4444).copy(alpha = 0.1f),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.2f))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.AutoMirrored.Rounded.Logout, null, tint = Color(0xFFEF4444))
                        Spacer(Modifier.width(12.dp))
                        Text("Sign Out", color = Color(0xFFEF4444), fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(Modifier.height(100.dp))
        }
    }

    if (showSignInPrompt) {
        AlertDialog(
            onDismissRequest = { showSignInPrompt = false },
            title = { Text("Authentication Required", fontWeight = FontWeight.Bold) },
            text = { Text("Please sign in or create a RentEasy account to access this feature.") },
            confirmButton = {
                Button(
                    onClick = {
                        showSignInPrompt = false
                        onLogout()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Sign In", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showSignInPrompt = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun StatBox(modifier: Modifier, value: String, label: String) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
            Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun ProfileMenuSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
        Text(title, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 4.dp, bottom = 12.dp))
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(20.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            Column {
                content()
            }
        }
    }
}

@Composable
fun MenuRow(icon: ImageVector, label: String, onClick: () -> Unit = {}, badge: @Composable (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.size(36.dp)
        ) {
            Icon(icon, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(8.dp))
        }
        Spacer(Modifier.width(16.dp))
        Text(label, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
        if (badge != null) {
            badge()
            Spacer(Modifier.width(8.dp))
        }
        Icon(Icons.Rounded.ChevronRight, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
    }
}
