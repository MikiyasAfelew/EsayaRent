package com.renteasy.app.ui.screens.owner

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.renteasy.app.ui.theme.*
import coil.compose.AsyncImage
import com.renteasy.app.domain.model.Property
import com.renteasy.app.domain.model.PropertyStatus
import com.renteasy.app.domain.model.Booking

@Composable
fun OwnerDashboardScreen(
    onAddListing: () -> Unit,
    onNavigateToVerification: () -> Unit,
    onNavigateToMyListings: () -> Unit,
    viewModel: OwnerDashboardViewModel = hiltViewModel()
) {
    val myProperties by viewModel.ownerProperties.collectAsState()
    val approvedCount by viewModel.approvedCount.collectAsState()
    val pendingBookings by viewModel.pendingBookings.collectAsState()
    val ownerName by viewModel.ownerName.collectAsState()
    val totalEarnings by viewModel.totalEarnings.collectAsState()
    val occupancyRate by viewModel.occupancyRate.collectAsState()
    val verificationLevel by viewModel.verificationLevel.collectAsState()

    var showVerificationDialog by remember { mutableStateOf(false) }
    var showPendingDialog by remember { mutableStateOf(false) }

    val handleAddListingClick = {
        when (verificationLevel) {
            com.renteasy.app.domain.model.UserVerificationLevel.FULLY_VERIFIED,
            com.renteasy.app.domain.model.UserVerificationLevel.ID_VERIFIED -> {
                onAddListing()
            }
            com.renteasy.app.domain.model.UserVerificationLevel.PENDING_REVIEW -> {
                showPendingDialog = true
            }
            else -> {
                showVerificationDialog = true
            }
        }
    }

    if (showVerificationDialog) {
        AlertDialog(
            onDismissRequest = { showVerificationDialog = false },
            title = { Text("Identity Verification Required", fontWeight = FontWeight.Bold) },
            text = { Text("To comply with local renting regulations and protect our users, you must verify your identity before adding listings.") },
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

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            OwnerTopBar()
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = handleAddListingClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 16.dp, end = 16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Listing", modifier = Modifier.size(32.dp))
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(vertical = 16.dp)) {
                    Text(
                        text = "Welcome Back,",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = ownerName,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Here is an overview of your rental business.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Spacer(Modifier.height(8.dp))
            }

            // --- Business Overview Cards ---
            item {
                EarningsCard(
                    totalEarnings = String.format("%,d", totalEarnings)
                )
                Spacer(Modifier.height(16.dp))
            }

            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    ActiveListingsCard(
                        modifier = Modifier.weight(1.1f).clickable { onNavigateToMyListings() },
                        count = approvedCount
                    )
                    Spacer(Modifier.width(16.dp))
                     OccupancyCard(
                         modifier = Modifier.weight(1f),
                         rate = occupancyRate
                     )
                }
                Spacer(Modifier.height(24.dp))
            }

            // --- Pending Bookings Section ---
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Pending Booking Requests",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(200.dp)
                    )
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
                    ) {
                        Text("View All", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                    }
                }
                Text(
                    "Manage new inquiries for your properties",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Live Booking Requests from Firestore
            if (pendingBookings.isEmpty()) {
                item {
                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                    ) {
                        Box(modifier = Modifier.padding(20.dp), contentAlignment = Alignment.Center) {
                            Text("No pending requests.", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
                        }
                    }
                }
            } else {
                items(pendingBookings) { request ->
                    BookingRequestItem(
                        booking = request,
                        onAccept = { viewModel.acceptBooking(request.id) },
                        onReject = { viewModel.rejectBooking(request.id) }
                    )
                    Spacer(Modifier.height(12.dp))
                }
                item {
                    Spacer(Modifier.height(12.dp))
                }
            }

            // --- My Properties List ---
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "My Listings",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(onClick = onNavigateToMyListings) {
                        Text("Manage All", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            if (myProperties.isEmpty()) {
                item {
                    EmptyListingsPlaceholder(handleAddListingClick)
                }
            } else {
                items(myProperties) { property ->
                    OwnerPropertyCard(property = property, onClick = onNavigateToMyListings)
                    Spacer(Modifier.height(12.dp))
                }
            }
            
            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerTopBar() {
    TopAppBar(
        title = { Text("RentEasy", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Black) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
        actions = {
            IconButton(onClick = { }) {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.onBackground)
            }
            IconButton(onClick = { }) {
                Icon(Icons.Outlined.LocationOn, contentDescription = "Location", tint = MaterialTheme.colorScheme.onBackground)
            }
        }
    )
}

@Composable
fun EarningsCard(totalEarnings: String, trend: String? = null) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Surface(
                    color = Color.White.copy(alpha = 0.05f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        Icons.Outlined.Payments, 
                        contentDescription = null, 
                        tint = MaterialTheme.colorScheme.onBackground, 
                        modifier = Modifier.padding(8.dp)
                    )
                }
                if (trend != null) {
                    Surface(
                        color = VerifiedGreen.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = trend,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            color = VerifiedGreen,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
            Text("TOTAL EARNINGS (NET)", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelMedium)
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = totalEarnings, 
                    style = MaterialTheme.typography.headlineLarge, 
                    color = MaterialTheme.colorScheme.onBackground, 
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = " ETB", 
                    style = MaterialTheme.typography.titleMedium, 
                    color = MaterialTheme.colorScheme.onSurfaceVariant, 
                    modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                "After 5% RentEasy platform fee",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun ActiveListingsCard(modifier: Modifier, count: Int) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Surface(
                color = Color.White.copy(alpha = 0.05f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    Icons.Outlined.Apartment, 
                    contentDescription = null, 
                    tint = MaterialTheme.colorScheme.onBackground, 
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(Modifier.height(16.dp))
            Text("ACTIVE LISTINGS", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(text = "$count", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
            Text(text = "Properties", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
        }
    }
}

@Composable
fun OccupancyCard(modifier: Modifier, rate: Int) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.BarChart, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(4.dp))
                Text("Occupancy Rate", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp)
            }
            Spacer(Modifier.height(12.dp))
            Spacer(Modifier.height(12.dp))
            Spacer(Modifier.height(12.dp))
            Text(text = "$rate%", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { rate / 100f },
                modifier = Modifier.fillMaxWidth().height(4.dp).clip(CircleShape),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color.White.copy(alpha = 0.1f)
            )
        }
    }
}

@Composable
fun BookingRequestItem(
    booking: Booking,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    val dateFormatter = remember { java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()) }
    val dateText = "${dateFormatter.format(java.util.Date(booking.startDate))} - ${dateFormatter.format(java.util.Date(booking.endDate))}"

    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Outlined.Person, null, tint = MaterialTheme.colorScheme.primary)
                    }
                }
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Verified Renter", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(Modifier.width(8.dp))
                        Surface(
                            color = VerifiedGreen.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                "VERIFIED",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                color = VerifiedGreen,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Text(booking.propertyTitle, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp, maxLines = 1)
                }
                Icon(Icons.Default.Notifications, contentDescription = null, tint = VerifiedGreen, modifier = Modifier.size(20.dp))
            }
            
            Spacer(Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Outlined.CalendarToday, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp))
                Spacer(Modifier.width(6.dp))
                Text(dateText, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                Spacer(Modifier.width(16.dp))
                Icon(Icons.Outlined.Payments, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp))
                Spacer(Modifier.width(6.dp))
                Text("${booking.totalPrice} ETB", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
            }
            
            Spacer(Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = onAccept,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Accept", color = Color.White, fontWeight = FontWeight.Bold)
                }
                OutlinedButton(
                    onClick = onReject,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Text("Reject", color = MaterialTheme.colorScheme.onBackground)
                }
            }
        }
    }
}

@Composable
fun OwnerPropertyCard(property: Property, onClick: () -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            com.renteasy.app.ui.components.RentEasyImage(
                model = property.images.firstOrNull(),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(property.title, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(property.location, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("${property.pricePerMonth} ${property.currency}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("/mo", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                }
            }
            PropertyStatusChip(property.status)
        }
    }
}

@Composable
fun PropertyStatusChip(status: PropertyStatus) {
    val (color, bgColor) = when (status) {
        PropertyStatus.APPROVED, PropertyStatus.AVAILABLE -> VerifiedGreen to VerifiedGreen.copy(alpha = 0.1f)
        PropertyStatus.PENDING, PropertyStatus.RESERVED -> Color(0xFFFFC107) to Color(0xFFFFC107).copy(alpha = 0.1f)
        PropertyStatus.REJECTED, PropertyStatus.RENTED -> Color.Red to Color.Red.copy(alpha = 0.1f)
    }
    Surface(
        color = bgColor,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = status.name,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = color,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun EmptyListingsPlaceholder(onAddListing: () -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().height(200.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Outlined.Apartment, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(48.dp))
            Spacer(Modifier.height(16.dp))
            Text("No listings yet", color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold)
            Text("Start earning by listing your property", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onAddListing,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("List a Property", color = Color.White)
            }
        }
    }
}
