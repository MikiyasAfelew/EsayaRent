package com.renteasy.app.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.renteasy.app.domain.model.Amenity
import com.renteasy.app.domain.model.AmenityIcon
import com.renteasy.app.domain.model.Property
import com.renteasy.app.domain.model.PropertyStatus
import com.renteasy.app.ui.screens.home.VerifiedBadge
import com.renteasy.app.ui.theme.*

import com.renteasy.app.ui.navigation.AuthViewModel

@Composable
fun PropertyDetailScreen(
    propertyId: String,
    onBack: () -> Unit,
    onBookNow: () -> Unit,
    onMessageClick: (String, String, String, String, String) -> Unit,
    onNavigateToVerification: () -> Unit,
    onSignInRequired: () -> Unit = {},
    viewModel: PropertyDetailViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    LaunchedEffect(propertyId) {
        viewModel.setPropertyId(propertyId)
    }

    val property by viewModel.property.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState(initial = null)
    val isGuest = currentUser == null
    var showSignInPrompt by remember { mutableStateOf(false) }
    var showVerificationDialog by remember { mutableStateOf(false) }
    var showPendingDialog by remember { mutableStateOf(false) }
    val isFavourite by viewModel.isFavourite.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        if (property == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else {
            val p = property!!
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                // ── Hero Image ────────────────────────────────────────────────────
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(280.dp)) {
                        com.renteasy.app.ui.components.RentEasyImage(
                            model = p.images.firstOrNull(),
                            contentDescription = p.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color(0xBB000000), Color.Transparent)
                                    )
                                )
                        )
                        if (p.isVerified) {
                            VerifiedBadge(
                                modifier = Modifier.align(Alignment.TopEnd)
                                    .padding(top = 60.dp, end = 16.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .statusBarsPadding()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconCircleButton(Icons.AutoMirrored.Rounded.ArrowBack, "Back") { onBack() }
                            Spacer(Modifier.weight(1f))
                            Text(
                                "RentEasy",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )
                            Spacer(Modifier.weight(1f))
                            IconCircleButton(Icons.Outlined.Share, "Share") {}
                            Spacer(Modifier.width(10.dp))
                             IconCircleButton(
                                 if (isFavourite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                                 "Favourite",
                                 tint = if (isFavourite) Color(0xFFEF4444) else MaterialTheme.colorScheme.onBackground
                             ) {
                                 if (isGuest) {
                                     showSignInPrompt = true
                                 } else {
                                     viewModel.toggleFavourite()
                                 }
                             }
                        }
                    }
                }

                // ── Title + Price ─────────────────────────────────────────────────
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 20.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = p.title,
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(Modifier.height(6.dp))
                            val (badgeText, badgeBgColor, badgeTxtColor) = when (p.status) {
                                PropertyStatus.RENTED -> Triple("Already Rented", Color(0xFFFEE2E2), Color(0xFFEF4444))
                                PropertyStatus.RESERVED -> Triple("Reserved (Pending Payment)", Color(0xFFFEF3C7), Color(0xFFD97706))
                                else -> Triple("Available Now", Color(0xFFDCFCE7), VerifiedGreen)
                            }
                            Surface(
                                color = badgeBgColor,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = badgeText,
                                    color = badgeTxtColor,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Spacer(Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Outlined.LocationOn, null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(15.dp)
                                )
                                Spacer(Modifier.width(3.dp))
                                Text(
                                    text = p.location,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "%,d".format(p.pricePerMonth),
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    color = PriceBlue, fontWeight = FontWeight.ExtraBold
                                )
                            )
                            Text(
                                text = "ETB/mo",
                                style = MaterialTheme.typography.bodySmall,
                                color = PriceBlue
                            )
                        }
                    }
                }

                // ── Specs row ─────────────────────────────────────────────────────
                item {
                    HorizontalDivider(color = DarkDivider, modifier = Modifier.padding(horizontal = 20.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 20.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SpecCard(Icons.Rounded.Bed, "${p.bedrooms}", "Bedrooms")
                        SpecCard(Icons.Rounded.Bathtub, "${p.bathrooms}", "Bathrooms")
                        SpecCard(Icons.Rounded.SquareFoot, "${p.areaM2}", "sqm")
                    }
                    HorizontalDivider(color = DarkDivider, modifier = Modifier.padding(horizontal = 20.dp))
                }

                // ── Description ───────────────────────────────────────────────────
                item {
                    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)) {
                        SectionTitle("Description")
                        Spacer(Modifier.height(10.dp))
                        Text(
                            text = p.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 22.sp
                        )
                    }
                    HorizontalDivider(color = DarkDivider, modifier = Modifier.padding(horizontal = 20.dp))
                }

                // ── Owner ─────────────────────────────────────────────────────────
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 20.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            if (!p.ownerAvatarUrl.isNullOrBlank()) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(p.ownerAvatarUrl)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = p.ownerName,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                Text(
                                    text = p.ownerName.take(1),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Spacer(Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = p.ownerName,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Rounded.Star, null, tint = Color(0xFFFACC15), modifier = Modifier.size(14.dp))
                                Spacer(Modifier.width(3.dp))
                                Text("${p.ownerRating}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        IconCircleButton(Icons.AutoMirrored.Outlined.Message, "Message owner") {
                            if (isGuest) {
                                showSignInPrompt = true
                            } else {
                                onMessageClick(p.ownerId, p.ownerName, p.title, p.pricePerMonth.toString(), p.images.firstOrNull() ?: "")
                            }
                        }
                    }
                }
            }

            // ── Book Now CTA ──────────────────────────────────────────────────
            val isBookable = p.status != PropertyStatus.RENTED && p.status != PropertyStatus.RESERVED
            val buttonText = when (p.status) {
                PropertyStatus.RENTED -> "Already Rented / Booked"
                PropertyStatus.RESERVED -> "Reserved (Pending Payment)"
                else -> "Book Now"
            }
            val buttonColor = when (p.status) {
                PropertyStatus.RENTED -> Color.Gray
                PropertyStatus.RESERVED -> Color(0xFFD97706) // Amber/Orange
                else -> MaterialTheme.colorScheme.primary
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.background),
                            startY = 0f
                        )
                    )
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                Button(
                    onClick = {
                        if (isGuest) {
                            showSignInPrompt = true
                        } else {
                            val user = currentUser
                            if (user != null) {
                                when (user.verificationLevel) {
                                    com.renteasy.app.domain.model.UserVerificationLevel.FULLY_VERIFIED,
                                    com.renteasy.app.domain.model.UserVerificationLevel.ID_VERIFIED -> {
                                        onBookNow()
                                    }
                                    com.renteasy.app.domain.model.UserVerificationLevel.PENDING_REVIEW -> {
                                        showPendingDialog = true
                                    }
                                    else -> {
                                        showVerificationDialog = true
                                    }
                                }
                            }
                        }
                    },
                    enabled = isBookable,
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor,
                        disabledContainerColor = Color(0xFF374151),
                        contentColor = Color.White,
                        disabledContentColor = Color.LightGray
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(buttonText, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    if (showSignInPrompt) {
        AlertDialog(
            onDismissRequest = { showSignInPrompt = false },
            title = { Text("Authentication Required", fontWeight = FontWeight.Bold) },
            text = { Text("Please sign in or create a RentEasy account to book properties or chat with owners.") },
            confirmButton = {
                Button(
                    onClick = {
                        showSignInPrompt = false
                        onSignInRequired()
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

    if (showVerificationDialog) {
        AlertDialog(
            onDismissRequest = { showVerificationDialog = false },
            title = { Text("Identity Verification Required", fontWeight = FontWeight.Bold) },
            text = { Text("To comply with local renting regulations and protect our users, you must verify your identity before booking properties.") },
            confirmButton = {
                Button(
                    onClick = {
                        showVerificationDialog = false
                        onNavigateToVerification()
                    }
                ) {
                    Text("Verify Now", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showVerificationDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showPendingDialog) {
        AlertDialog(
            onDismissRequest = { showPendingDialog = false },
            title = { Text("Verification Under Review", fontWeight = FontWeight.Bold) },
            text = { Text("Your identity documents are currently pending review. We will notify you as soon as your account is approved.") },
            confirmButton = {
                Button(
                    onClick = {
                        showPendingDialog = false
                        onNavigateToVerification()
                    }
                ) {
                    Text("Check Status", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showPendingDialog = false }) {
                    Text("Dismiss")
                }
            }
        )
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text = text, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground)
}

@Composable
private fun SpecCard(icon: ImageVector, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(26.dp))
        Spacer(Modifier.height(4.dp))
        Text(text = value, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onBackground)
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun IconCircleButton(icon: ImageVector, contentDescription: String, tint: Color = MaterialTheme.colorScheme.onBackground, onClick: () -> Unit) {
    Box(
        modifier = Modifier.size(38.dp).clip(CircleShape).background(Color(0x88000000)).clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription, tint = tint, modifier = Modifier.size(20.dp))
    }
}
