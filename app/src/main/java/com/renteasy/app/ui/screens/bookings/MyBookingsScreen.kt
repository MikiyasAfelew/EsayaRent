package com.renteasy.app.ui.screens.bookings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.renteasy.app.domain.model.Booking
import com.renteasy.app.domain.model.BookingStatus
import com.renteasy.app.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun MyBookingsScreen(
    onSignInRequired: () -> Unit = {},
    onContactOwner: (String, String, String, String, String) -> Unit,
    viewModel: MyBookingsViewModel = hiltViewModel()
) {
    val currentUser by viewModel.currentUser.collectAsState(initial = null)
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Active", "Past Bookings", "Canceled")

    val allBookings by viewModel.renterBookings.collectAsState()

    // Filter bookings based on selected tab
    val filteredBookings = remember(allBookings, selectedTab) {
        when (selectedTab) {
            0 -> allBookings.filter { it.status == BookingStatus.PENDING || it.status == BookingStatus.CONFIRMED }
            1 -> allBookings.filter { it.status == BookingStatus.COMPLETED }
            else -> allBookings.filter { it.status == BookingStatus.CANCELLED }
        }
    }

    val activeConfirmedBooking = remember(allBookings) {
        allBookings.firstOrNull { it.status == BookingStatus.CONFIRMED }
    }

    if (currentUser == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = CircleShape,
                        modifier = Modifier.size(80.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Icon(
                                Icons.Rounded.Lock,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                    Text(
                        text = "Access Your Bookings",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "Sign in or create a RentEasy account to track your rental history and manage active property reservations.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        lineHeight = 20.sp
                    )
                    Spacer(Modifier.height(32.dp))
                    Button(
                        onClick = onSignInRequired,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Sign In or Register", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                    }
                }
            }
        }
    } else {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                Column {
                    CenterAlignedTopAppBar(
                        title = { Text("My Bookings", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold) },
                        actions = {
                            IconButton(onClick = { }) {
                                Icon(Icons.Rounded.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground)
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.background)
                    )
                    
                    ScrollableTabRow(
                        selectedTabIndex = selectedTab,
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.primary,
                        edgePadding = 20.dp,
                        divider = {},
                        indicator = { }
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                Surface(
                                    color = if (selectedTab == index) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(12.dp),
                                    border = if (selectedTab == index) BorderStroke(1.dp, MaterialTheme.colorScheme.primary) else null,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        title,
                                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                                        style = MaterialTheme.typography.labelLarge,
                                        color = if (selectedTab == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(20.dp)
            ) {
                item {
                    Text(
                        "Manage your current and historical property rentals.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(16.dp))
                }

                if (activeConfirmedBooking != null) {
                    item {
                        RentPaymentReminderCard(booking = activeConfirmedBooking)
                        Spacer(Modifier.height(16.dp))
                    }
                }

                if (filteredBookings.isEmpty()) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().height(250.dp), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Rounded.FolderOpen, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(48.dp))
                                Spacer(Modifier.height(16.dp))
                                Text("No bookings found", color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                } else {
                    items(filteredBookings) { booking ->
                        BookingCard(
                            booking = booking,
                            onCancelClick = { viewModel.cancelBooking(booking.id) },
                            onContactOwner = onContactOwner
                        )
                        Spacer(Modifier.height(16.dp))
                    }
                }

                item {
                    SecuredPaymentBanner()
                    Spacer(Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
fun BookingCard(
    booking: Booking,
    onCancelClick: () -> Unit,
    onContactOwner: (String, String, String, String, String) -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val dateFormatter = remember { SimpleDateFormat("MMM dd", Locale.getDefault()) }
    val startDateStr = dateFormatter.format(Date(booking.startDate))
    val endDateStr = dateFormatter.format(Date(booking.endDate))
    val yearStr = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date(booking.startDate))
    val dateRangeText = "$startDateStr - $endDateStr, $yearStr"

    var showReceiptDialog by remember { mutableStateOf(false) }
    var showDropdownMenu by remember { mutableStateOf(false) }
    var showCancelConfirmation by remember { mutableStateOf(false) }

    if (showReceiptDialog) {
        ReceiptDialog(booking = booking, onDismiss = { showReceiptDialog = false })
    }

    if (showCancelConfirmation) {
        AlertDialog(
            onDismissRequest = { showCancelConfirmation = false },
            title = { Text("Cancel Reservation?", fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to cancel this booking for ${booking.propertyTitle}? If you have paid, refunds will be processed based on cancellation policy.") },
            confirmButton = {
                Button(
                    onClick = {
                        showCancelConfirmation = false
                        onCancelClick()
                        android.widget.Toast.makeText(context, "Reservation Cancelled", android.widget.Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Yes, Cancel", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showCancelConfirmation = false }) {
                    Text("No, Keep")
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }

    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            com.renteasy.app.ui.components.RentEasyImage(
                model = booking.propertyImageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(160.dp).clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(booking.propertyTitle, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, maxLines = 1, modifier = Modifier.weight(1f))
                Spacer(Modifier.width(8.dp))
                
                val statusText = when (booking.status) {
                    BookingStatus.CONFIRMED -> "Confirmed"
                    BookingStatus.PENDING -> "Pending"
                    BookingStatus.CANCELLED -> "Canceled"
                    BookingStatus.COMPLETED -> "Completed"
                }

                val statusColor = when (booking.status) {
                    BookingStatus.CONFIRMED -> VerifiedGreen
                    BookingStatus.PENDING -> MaterialTheme.colorScheme.primary
                    BookingStatus.CANCELLED -> Color.Red
                    BookingStatus.COMPLETED -> Color.Gray
                }

                Surface(
                    color = statusColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            when (booking.status) {
                                BookingStatus.CONFIRMED -> Icons.Rounded.CheckCircle
                                BookingStatus.PENDING -> Icons.Rounded.HourglassEmpty
                                BookingStatus.CANCELLED -> Icons.Rounded.Cancel
                                BookingStatus.COMPLETED -> Icons.Rounded.DoneAll
                            },
                            contentDescription = null,
                            tint = statusColor,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            statusText,
                            style = MaterialTheme.typography.labelSmall,
                            color = statusColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Rounded.CalendarToday, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(8.dp))
                Text(dateRangeText, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Rounded.Payments, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(8.dp))
                Text("${booking.totalPrice} ETB Total", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            
            if (booking.status == BookingStatus.PENDING || booking.status == BookingStatus.CONFIRMED) {
                Spacer(Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    if (booking.status == BookingStatus.CONFIRMED) {
                        Button(
                            onClick = { showReceiptDialog = true },
                            modifier = Modifier.weight(1f).height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("View Receipt", color = Color.White)
                        }
                    } else {
                        OutlinedButton(
                            onClick = { showCancelConfirmation = true },
                            modifier = Modifier.weight(1f).height(48.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Cancel Request", color = MaterialTheme.colorScheme.onBackground)
                        }
                    }
                    Spacer(Modifier.width(12.dp))
                    Box {
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    if (booking.status == BookingStatus.CONFIRMED) {
                                        showDropdownMenu = true
                                    } else {
                                        onContactOwner(
                                            booking.ownerId,
                                            "Property Owner",
                                            booking.propertyTitle,
                                            "${booking.totalPrice} ETB",
                                            booking.propertyImageUrl
                                        )
                                    }
                                }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (booking.status == BookingStatus.CONFIRMED) Icons.Rounded.MoreHoriz else Icons.Rounded.ChatBubbleOutline,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }

                        if (booking.status == BookingStatus.CONFIRMED) {
                            DropdownMenu(
                                expanded = showDropdownMenu,
                                onDismissRequest = { showDropdownMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Contact Owner") },
                                    onClick = {
                                        showDropdownMenu = false
                                        onContactOwner(
                                            booking.ownerId,
                                            "Property Owner",
                                            booking.propertyTitle,
                                            "${booking.totalPrice} ETB",
                                            booking.propertyImageUrl
                                        )
                                    },
                                    leadingIcon = { Icon(Icons.Rounded.ChatBubbleOutline, contentDescription = null) }
                                )
                                DropdownMenuItem(
                                    text = { Text("View Rules & Policies") },
                                    onClick = {
                                        showDropdownMenu = false
                                        android.widget.Toast.makeText(context, "House Rules: Non-smoking, No pets, Standard checkout at 11 AM.", android.widget.Toast.LENGTH_LONG).show()
                                    },
                                    leadingIcon = { Icon(Icons.Rounded.Info, contentDescription = null) }
                                )
                                DropdownMenuItem(
                                    text = { Text("Cancel Booking") },
                                    onClick = {
                                        showDropdownMenu = false
                                        showCancelConfirmation = true
                                    },
                                    leadingIcon = { Icon(Icons.Rounded.Cancel, contentDescription = null) }
                                )
                            }
                        }
                    }
                }
            } else if (booking.status == BookingStatus.COMPLETED) {
                Spacer(Modifier.height(20.dp))
                var showRatingDialog by remember { mutableStateOf(false) }
                
                if (showRatingDialog) {
                    RatingDialog(booking = booking, onDismiss = { showRatingDialog = false })
                }
                
                Button(
                    onClick = { showRatingDialog = true },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Rounded.Star, null, tint = Color.White, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Rate Property & Stay", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun ReceiptDialog(
    booking: Booking,
    onDismiss: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val dateFormatter = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }
    val startDateStr = dateFormatter.format(Date(booking.startDate))
    val endDateStr = dateFormatter.format(Date(booking.endDate))
    
    val basePrice = (booking.totalPrice * 0.95).toInt()
    val serviceFee = booking.totalPrice - basePrice

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Close Receipt", fontWeight = FontWeight.Bold, color = Color.White)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = {
                    android.widget.Toast.makeText(context, "Receipt PDF downloaded successfully!", android.widget.Toast.LENGTH_SHORT).show()
                },
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                modifier = Modifier.fillMaxWidth().height(50.dp).padding(top = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Rounded.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Download PDF Receipt", color = MaterialTheme.colorScheme.onSurface)
                }
            }
        },
        shape = RoundedCornerShape(24.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = CircleShape,
                    color = VerifiedGreen.copy(alpha = 0.1f),
                    modifier = Modifier.size(60.dp)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ReceiptLong,
                            contentDescription = null,
                            tint = VerifiedGreen,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    "RentEasy Receipt",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    "Transaction Confirmed",
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = VerifiedGreen
                )
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
                Spacer(Modifier.height(16.dp))

                // Property Summary
                Text("RENTED PROPERTY", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(4.dp))
                Text(booking.propertyTitle, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                
                Spacer(Modifier.height(16.dp))

                // Stay Duration
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("CHECK-IN", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.height(4.dp))
                        Text(startDateStr, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("CHECK-OUT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.height(4.dp))
                        Text(endDateStr, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                    }
                }

                Spacer(Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
                Spacer(Modifier.height(16.dp))

                // Pricing Details
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Base Rent", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
                    Text("$basePrice ETB", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                }
                Spacer(Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("RentEasy System Cut (5%)", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
                    Text("$serviceFee ETB", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                }
                Spacer(Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Processing & Tax", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
                    Text("0 ETB (Free)", color = VerifiedGreen, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                }

                Spacer(Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
                Spacer(Modifier.height(16.dp))

                // Grand Total
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Total Paid", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
                    Text("${booking.totalPrice} ETB", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
                }

                Spacer(Modifier.height(24.dp))

                // Reference & Security
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "REFERENCE ID: ${booking.id.take(12).uppercase()}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(12.dp))
                    
                    // Styled Security ESCROW Badge
                    Surface(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Rounded.VerifiedUser,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                "Escrow Protection Active",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    )
}


@Composable
fun SecuredPaymentBanner() {
    Surface(
        color = Color(0xFF1E1B4B),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                modifier = Modifier.size(44.dp)
            ) {
                Icon(Icons.Rounded.Shield, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(10.dp))
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text("Secured Payments", style = MaterialTheme.typography.titleSmall, color = Color.White)
                Text(
                    "Your funds are held in escrow and only released to the owner 24 hours after you check-in successfully.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.LightGray,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun RentPaymentReminderCard(booking: Booking) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Rounded.Notifications,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Rent Cycle Reminder",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Your next rent billing cycle for ${booking.propertyTitle} approaches in 5 days. Ensure your CBE or Telebirr account is funded.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun RatingDialog(
    booking: Booking,
    onDismiss: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    var rating by remember { mutableStateOf(5) }
    var comment by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Rate Your Stay",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "How was your stay at ${booking.propertyTitle}?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 1..5) {
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = null,
                            tint = if (i <= rating) Color(0xFFFFC107) else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                            modifier = Modifier
                                .size(36.dp)
                                .clickable { rating = i }
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    label = { Text("Write your review (optional)") },
                    placeholder = { Text("Share your experience with other renters...") },
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onDismiss()
                    android.widget.Toast.makeText(context, "Thank you! Your stay rating has been submitted.", android.widget.Toast.LENGTH_LONG).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Submit Review", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        shape = RoundedCornerShape(24.dp)
    )
}

