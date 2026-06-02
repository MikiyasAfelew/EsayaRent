package com.renteasy.app.ui.screens.verification

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.renteasy.app.domain.model.UserVerificationLevel
import com.renteasy.app.ui.navigation.AuthViewModel
import com.renteasy.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerificationStatusScreen(
    onBack: () -> Unit,
    onContactSupport: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val currentUser by viewModel.currentUser.collectAsState()
    val verificationLevel = currentUser?.verificationLevel ?: UserVerificationLevel.BASIC
    val isVerified = verificationLevel == UserVerificationLevel.ID_VERIFIED || verificationLevel == UserVerificationLevel.FULLY_VERIFIED

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        if (isVerified) "Verified!" else "Verification Status",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(40.dp))

            if (isVerified) {
                // ──── VERIFIED SUCCESS STATE ────────────────────────────────────
                VerifiedSuccessContent()
            } else {
                // ──── PENDING REVIEW STATE ──────────────────────────────────────
                PendingReviewContent()
            }

            Spacer(Modifier.weight(1f))

            // --- Footer Buttons ---
            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isVerified) VerifiedGreen else MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    if (isVerified) "Back to Profile" else "Back to Home",
                    fontWeight = FontWeight.Bold
                )
            }

            if (!isVerified) {
                Spacer(Modifier.height(12.dp))
                OutlinedButton(
                    onClick = onContactSupport,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Text("Contact Support", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(Modifier.height(32.dp))
        }
    }
}

// ── Verified Success ────────────────────────────────────────────────────────────

@Composable
private fun VerifiedSuccessContent() {
    // Pulse animation for the check icon
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    // Green gradient circle with animated check
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(160.dp)) {
        Surface(
            shape = CircleShape,
            color = Color.Transparent,
            modifier = Modifier
                .size(160.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            VerifiedGreen.copy(alpha = 0.15f),
                            VerifiedGreen.copy(alpha = 0.05f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        ) {}
        Surface(
            shape = CircleShape,
            color = VerifiedGreen,
            modifier = Modifier.size(100.dp).scale(scale)
        ) {
            Icon(
                Icons.Rounded.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(24.dp)
            )
        }
    }

    Spacer(Modifier.height(32.dp))
    Text(
        "Identity Verified!",
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
    Spacer(Modifier.height(12.dp))
    Text(
        "Congratulations! Your identity has been verified. You now have full access to all RentEasy features.",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center
    )

    Spacer(Modifier.height(32.dp))

    // Benefits unlocked
    Surface(
        color = VerifiedGreen.copy(alpha = 0.08f),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "✅ Benefits Unlocked",
                style = MaterialTheme.typography.titleSmall,
                color = VerifiedGreen,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(12.dp))
            BenefitItem("Verified badge on your profile")
            BenefitItem("Access to book any listed property")
            BenefitItem("Direct messaging with owners")
            BenefitItem("Priority support from RentEasy team")
        }
    }
}

@Composable
private fun BenefitItem(text: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Rounded.CheckCircle, null, tint = VerifiedGreen, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(10.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground)
    }
}

// ── Pending Review ──────────────────────────────────────────────────────────────

@Composable
private fun PendingReviewContent() {
    Box(contentAlignment = Alignment.BottomEnd) {
        Surface(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            shape = CircleShape,
            modifier = Modifier.size(160.dp)
        ) {
            Icon(
                Icons.Rounded.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(40.dp)
            )
        }
        Surface(
            color = Color(0xFFF59E0B),
            shape = CircleShape,
            modifier = Modifier.size(44.dp).border(4.dp, MaterialTheme.colorScheme.background, CircleShape)
        ) {
            Icon(Icons.Rounded.AccessTime, null, tint = Color.White, modifier = Modifier.padding(8.dp))
        }
    }

    Spacer(Modifier.height(32.dp))
    Text(
        text = "We're Verifying Your Details",
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
    Spacer(Modifier.height(12.dp))
    Text(
        text = "Thank you for submitting your documents. Our team is currently reviewing your application. This usually takes 24–48 hours.",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center
    )

    Spacer(Modifier.height(32.dp))

    // --- Timeline ---
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            TimelineItem(
                title = "Documents Submitted",
                subtitle = "Completed — your ID and selfie were received",
                status = "completed",
                isLast = false
            )
            TimelineItem(
                title = "Under Review",
                subtitle = "Estimated: 24–48 hours",
                status = "current",
                isLast = false
            )
            TimelineItem(
                title = "Verification Complete",
                subtitle = "Full access to RentEasy features",
                status = "pending",
                isLast = true
            )
        }
    }

    Spacer(Modifier.height(24.dp))

    // --- What Happens Next ---
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Rounded.Info, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("What happens next?", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(12.dp))
            NextStepItem("1.", "Our admin team reviews your uploaded ID and selfie.")
            NextStepItem("2.", "We perform a standard trust and safety check.")
            NextStepItem("3.", "Your Verified Badge is activated on your profile.")
        }
    }
}

@Composable
fun TimelineItem(title: String, subtitle: String, status: String, isLast: Boolean) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                shape = CircleShape,
                color = when(status) {
                    "completed" -> VerifiedGreen
                    "current" -> MaterialTheme.colorScheme.surface
                    else -> MaterialTheme.colorScheme.surface
                },
                modifier = Modifier.size(32.dp).border(
                    2.dp,
                    when(status) {
                        "completed" -> VerifiedGreen
                        "current" -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.outline
                    },
                    CircleShape
                )
            ) {
                if (status == "completed") {
                    Icon(Icons.Rounded.Check, null, tint = Color.White, modifier = Modifier.padding(6.dp))
                } else if (status == "pending") {
                    Icon(Icons.Rounded.Lock, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(8.dp))
                }
            }
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(40.dp)
                        .background(if (status == "completed") VerifiedGreen else MaterialTheme.colorScheme.outline)
                )
            }
        }
        Spacer(Modifier.width(16.dp))
        Column {
            Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun NextStepItem(num: String, text: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(num, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, modifier = Modifier.width(24.dp))
        Text(text, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
