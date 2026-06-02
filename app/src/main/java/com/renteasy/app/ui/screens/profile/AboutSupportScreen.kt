package com.renteasy.app.ui.screens.profile

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.renteasy.app.ui.theme.VerifiedGreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutSupportScreen(
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    var activeFaqIndex by remember { mutableStateOf<Int?>(null) }
    var supportSubject by remember { mutableStateOf("") }
    var supportMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var isSubmitted by remember { mutableStateOf(false) }

    val faqs = listOf(
        FaqItem(
            "How do I reserve a home?",
            "Browse properties on the discovery feed, select one to view its details, and tap 'Book Now'. Review dates, proceed to checkout, and complete payment using Chapa, Telebirr, CBE Birr or local cards."
        ),
        FaqItem(
            "Is my rental payment secure?",
            "Yes! RentEasy runs on fully secured bank-grade escrow gateways integrated with official secure APIs like Chapa. Funds are safely held until your check-in is complete."
        ),
        FaqItem(
            "How does RentEasy verify listings?",
            "Every verified property listing undergoes physical check-ups and title registry checks by RentEasy agents in Addis Ababa before receiving the VERIFIED badge."
        ),
        FaqItem(
            "What is the cancellation policy?",
            "Reservations can be canceled directly from the 'Bookings' panel. Full or partial refunds depend on the owner's set policy (flexible, moderate, or strict)."
        )
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "About & Support",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Brand Logo Header ---
            Surface(
                modifier = Modifier.size(80.dp),
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Rounded.Apartment,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(44.dp)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
            Text(
                "RentEasy",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                "Version 2.4.0 (Stable Release)",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(24.dp))

            // --- About US Section ---
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Our Mission",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "RentEasy is Ethiopia's premier luxury real estate network. We bridge the gap between renters and property owners by providing digital verification, automated checkouts, dynamic location maps, and guaranteed safety covenants.",
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 22.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            // --- FAQ Accordions ---
            Text(
                "Frequently Asked Questions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(Modifier.height(16.dp))

            faqs.forEachIndexed { index, faq ->
                FaqAccordionRow(
                    faq = faq,
                    expanded = activeFaqIndex == index,
                    onClick = {
                        activeFaqIndex = if (activeFaqIndex == index) null else index
                    }
                )
                Spacer(Modifier.height(12.dp))
            }

            Spacer(Modifier.height(32.dp))

            // --- Contact Support Form ---
            Text(
                "Contact Customer Care",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(Modifier.height(16.dp))

            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedTextField(
                        value = supportSubject,
                        onValueChange = { supportSubject = it },
                        label = { Text("Subject") },
                        placeholder = { Text("e.g. Refund issue, account setup") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        enabled = !isLoading
                    )

                    OutlinedTextField(
                        value = supportMessage,
                        onValueChange = { supportMessage = it },
                        label = { Text("Write your message") },
                        placeholder = { Text("Describe your issue in detail...") },
                        modifier = Modifier.fillMaxWidth().height(120.dp),
                        shape = RoundedCornerShape(12.dp),
                        enabled = !isLoading
                    )

                    Button(
                        onClick = {
                            if (supportSubject.isNotBlank() && supportMessage.isNotBlank()) {
                                isLoading = true
                                val user = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
                                val userId = user?.uid ?: "anonymous"
                                val userEmail = user?.email ?: "anonymous@renteasy.com"

                                val subjectCopy = supportSubject
                                val messageCopy = supportMessage

                                val ticket = hashMapOf(
                                    "id" to java.util.UUID.randomUUID().toString(),
                                    "userId" to userId,
                                    "userEmail" to userEmail,
                                    "subject" to subjectCopy,
                                    "message" to messageCopy,
                                    "timestamp" to System.currentTimeMillis(),
                                    "status" to "OPEN"
                                )

                                com.google.firebase.firestore.FirebaseFirestore.getInstance()
                                    .collection("support_tickets")
                                    .document(ticket["id"] as String)
                                    .set(ticket)
                                    .addOnSuccessListener {
                                        // Launch background silent email dispatch via Web3Forms API
                                        coroutineScope.launch {
                                            com.renteasy.app.data.api.SupportEmailSender.sendEmail(
                                                userEmail = userEmail,
                                                subject = subjectCopy,
                                                message = messageCopy,
                                                userId = userId
                                            )
                                        }

                                        isLoading = false
                                        isSubmitted = true
                                        supportSubject = ""
                                        supportMessage = ""
                                    }
                                    .addOnFailureListener { e ->
                                        isLoading = false
                                        Toast.makeText(context, "Failed to submit: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                                    }
                            } else {
                                Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.AutoMirrored.Rounded.Send, null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(8.dp))
                                Text("Send Message", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(48.dp))
        }
    }

    if (isSubmitted) {
        AlertDialog(
            onDismissRequest = { isSubmitted = false },
            title = { Text("Ticket Created Successfully!", fontWeight = FontWeight.Bold) },
            text = { Text("Thank you! Your ticket has been recorded in our Firebase secure registry and assigned to a support agent. We will contact you at your registered email address within 2 hours.") },
            confirmButton = {
                Button(
                    onClick = { isSubmitted = false },
                    colors = ButtonDefaults.buttonColors(containerColor = VerifiedGreen)
                ) {
                    Text("Understood", color = Color.White)
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }
}

data class FaqItem(val question: String, val answer: String)

@Composable
fun FaqAccordionRow(
    faq: FaqItem,
    expanded: Boolean,
    onClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    faq.question,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Rounded.ExpandLess else Icons.Rounded.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(animationSpec = spring(stiffness = Spring.StiffnessMediumLow)) + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column {
                    Spacer(Modifier.height(12.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                    Spacer(Modifier.height(12.dp))
                    Text(
                        faq.answer,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 20.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
