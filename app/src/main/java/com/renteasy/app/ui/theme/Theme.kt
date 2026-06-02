package com.renteasy.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary          = BrandBlue,
    onPrimary        = TextWhite,
    primaryContainer = BrandNavy,
    onPrimaryContainer = TextWhite,
    secondary        = VerifiedGreen,
    onSecondary      = TextWhite,
    secondaryContainer = VerifiedGreenBg,
    onSecondaryContainer = VerifiedGreen,
    background       = NightBackground,
    onBackground     = TextPrimary,
    surface          = NightSurface,
    onSurface        = TextPrimary,
    surfaceVariant   = NightSurfaceElev,
    onSurfaceVariant = TextSecondary,
    outline          = NightDivider,
    error            = ErrorRed,
    onError          = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary          = BrandBlueDark, // Premium solid indigo for beautiful contrast in light mode
    onPrimary        = Color.White,
    primaryContainer = Color(0xFFE0E7FF), // Soft premium lavender/indigo tint for cards/surfaces
    onPrimaryContainer = BrandBlueDark,
    secondary        = VerifiedGreen,
    onSecondary      = Color.White,
    secondaryContainer = Color(0xFFD1FAE5), // Soft mint container for success badges/verified badges
    onSecondaryContainer = Color(0xFF065F46),
    background       = LightBackground,
    onBackground     = LightTextPrimary,
    surface          = LightSurface,
    onSurface        = LightTextPrimary,
    surfaceVariant   = Color(0xFFF1F5F9), // Slate 100 for borders/divs
    onSurfaceVariant = LightTextSecondary,
    outline          = Color(0xFFE2E8F0), // Slate 200 for thin outlines
    error            = ErrorRed,
    onError          = Color.White
)

@Composable
fun RentEasyTheme(
    darkTheme: Boolean = true, // Dark by default per user request
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.surface.toArgb()
            
            val controller = WindowCompat.getInsetsController(window, view)
            controller.isAppearanceLightStatusBars = !darkTheme
            controller.isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        content     = content
    )
}
