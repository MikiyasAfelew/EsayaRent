package com.renteasy.app.ui.screens.welcome

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.renteasy.app.ui.theme.VerifiedGreen
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(
    onGetStarted: () -> Unit
) {
    // Staggered animation states
    var logoVisible by remember { mutableStateOf(false) }
    var contentVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(150)
        logoVisible = true
        delay(300)
        contentVisible = true
    }

    // Gentle pulsing glow on logo
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowScale"
    )

    val isDark by com.renteasy.app.ui.theme.ThemeController.isDarkMode.collectAsState()

    val backgroundColors = if (isDark) {
        listOf(
            Color(0xFF0F0F1A),
            Color(0xFF16162A),
            Color(0xFF121224)
        )
    } else {
        listOf(
            Color(0xFFF8FAFC),
            Color(0xFFEEF2F6),
            Color(0xFFE2E8F0)
        )
    }

    val glow1Color = if (isDark) Color(0xFF6366F1).copy(alpha = 0.22f) else Color(0xFF6366F1).copy(alpha = 0.08f)
    val glow2Color = if (isDark) Color(0xFF0CAF60).copy(alpha = 0.18f) else Color(0xFF0CAF60).copy(alpha = 0.06f)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = backgroundColors))
    ) {
        // Aesthetic ambient background glow
        Box(
            modifier = Modifier
                .size(350.dp)
                .offset(x = (-100).dp, y = (-80).dp)
                .blur(90.dp)
                .background(glow1Color, CircleShape)
        )
        Box(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 80.dp, y = 100.dp)
                .blur(90.dp)
                .background(glow2Color, CircleShape)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Spacer to push logo down
            Spacer(Modifier.height(40.dp))

            // Logo & Title Section
            AnimatedVisibility(
                visible = logoVisible,
                enter = fadeIn(tween(600)) + scaleIn(tween(600, easing = EaseOutBack))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .scale(glowScale)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFF6366F1),
                                        Color(0xFF4F46E5)
                                    )
                                ),
                                RoundedCornerShape(30.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Rounded.Apartment,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(56.dp)
                        )
                    }
                    
                    Spacer(Modifier.height(24.dp))
                    
                    Text(
                        "RentEasy",
                        fontSize = 44.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onBackground,
                        letterSpacing = (-1.5).sp
                    )
                    
                    Spacer(Modifier.height(8.dp))
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .width(20.dp)
                                .height(3.dp)
                                .background(VerifiedGreen, RoundedCornerShape(1.5.dp))
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Premium Rentals in Ethiopia",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .width(20.dp)
                                .height(3.dp)
                                .background(VerifiedGreen, RoundedCornerShape(1.5.dp))
                        )
                    }
                }
            }

            // Description & Button Section
            AnimatedVisibility(
                visible = contentVisible,
                enter = fadeIn(tween(600)) + slideInVertically(tween(600)) { it / 3 }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Welcome to RentEasy",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(Modifier.height(12.dp))
                    
                    Text(
                        "Explore verified homes, apartments, and offices. Fast, secure, and hassle-free rental experience across Ethiopia.",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center,
                        lineHeight = 24.sp
                    )
                    
                    Spacer(Modifier.height(48.dp))

                    Button(
                        onClick = onGetStarted,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Text(
                            "Get Started",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(Modifier.width(8.dp))
                        Icon(
                            Icons.Rounded.East,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    
                    Spacer(Modifier.height(30.dp))
                }
            }
        }
    }
}
