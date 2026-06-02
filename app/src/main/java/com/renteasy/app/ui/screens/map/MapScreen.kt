package com.renteasy.app.ui.screens.map

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.renteasy.app.domain.model.Property
import com.renteasy.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    onPropertyClick: (String) -> Unit,
    viewModel: MapViewModel = hiltViewModel()
) {
    val properties by viewModel.properties.collectAsState()
    val selectedProperty by viewModel.selectedProperty.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val widthDp = maxWidth
        val heightDp = maxHeight

        // --- Premium Custom Map Style Background ---
        AsyncImage(
            model = "https://static-maps.yandex.ru/1.x/?ll=38.789,9.008&z=14&l=map&size=600,600",
            contentDescription = "Map Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        
        // Dark translucent overlay for maximum badge readability and map aesthetic
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.15f))
        )

        // Bottom gradient fade to fully hide copyright text and unify the map with the theme
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(140.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.background.copy(alpha = 0.7f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        )

        // --- Top Bar & Search Indicators ---
        Column(modifier = Modifier.fillMaxSize()) {
            CenterAlignedTopAppBar(
                title = { 
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Rounded.LocationOn, 
                            null, 
                            tint = MaterialTheme.colorScheme.primary, 
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "RentEasy Map", 
                            color = MaterialTheme.colorScheme.onBackground, 
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Rounded.Search, null, tint = MaterialTheme.colorScheme.onBackground)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.9f)
                )
            )
            
            Spacer(Modifier.height(16.dp))
            
            // --- Search Area Button ---
            Surface(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                shape = RoundedCornerShape(30.dp),
                shadowElevation = 6.dp,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp), 
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Rounded.Refresh, 
                        null, 
                        tint = MaterialTheme.colorScheme.primary, 
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Search this area", 
                        color = MaterialTheme.colorScheme.primary, 
                        fontWeight = FontWeight.Bold, 
                        fontSize = 13.sp
                    )
                }
            }
        }

        // --- Dynamic stable price badges projected on the map ---
        if (properties.isNotEmpty()) {
            properties.forEach { property ->
                val seed = property.id.hashCode()
                
                // Deterministic, beautiful spacing around the map canvas
                val xFraction = 0.12f + (Math.abs(seed % 73) / 73f) * 0.70f
                val yFraction = 0.22f + (Math.abs((seed / 73) % 67) / 67f) * 0.48f

                val isSelected = selectedProperty?.id == property.id

                // Price display formatting (e.g. 15,500 -> 15.5k)
                val priceLabel = if (property.pricePerMonth >= 1000) {
                    val kValue = property.pricePerMonth / 1000.0
                    val formatted = if (property.pricePerMonth % 1000 == 0) {
                        "${kValue.toInt()}k"
                    } else {
                        "%.1f" .format(kValue) + "k"
                    }
                    "$formatted ETB"
                } else {
                    "${property.pricePerMonth} ETB"
                }

                PriceBadge(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(
                            x = widthDp * xFraction,
                            y = heightDp * yFraction
                        ),
                    price = priceLabel,
                    isDark = isSelected,
                    onClick = { viewModel.selectProperty(property) }
                )
            }
        }

        // --- Bottom Sheet/Card Overlay Container (Mutually Exclusive) ---
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 20.dp, vertical = 24.dp)
                .fillMaxWidth()
        ) {
            when {
                isLoading -> {
                    // Do nothing here; center loader handles it
                }
                properties.isEmpty() -> {
                    // Empty state placeholder
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(16.dp),
                        shadowElevation = 8.dp,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                    ) {
                        Row(
                            modifier = Modifier.padding(20.dp), 
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Rounded.HomeWork, 
                                null, 
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(
                                "No approved rentals available on map.", 
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
                selectedProperty != null -> {
                    // Selected Property Overlay Card
                    val property = selectedProperty!!
                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(24.dp),
                        shadowElevation = 10.dp,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Image Thumbnail
                            Box(
                                modifier = Modifier
                                    .size(96.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            ) {
                                AsyncImage(
                                    model = property.images.firstOrNull() ?: "https://images.unsplash.com/photo-1560518883-ce09059eeffa?auto=format&fit=crop&q=80&w=300",
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                if (property.isVerified) {
                                    Surface(
                                        modifier = Modifier
                                            .padding(6.dp)
                                            .align(Alignment.TopStart),
                                        color = VerifiedGreen,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp), 
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                Icons.Rounded.Shield, 
                                                null, 
                                                tint = Color.White, 
                                                modifier = Modifier.size(8.dp)
                                            )
                                            Spacer(Modifier.width(3.dp))
                                            Text(
                                                "VERIFIED", 
                                                color = Color.White, 
                                                fontSize = 7.sp, 
                                                fontWeight = FontWeight.Black
                                            )
                                        }
                                    }
                                }
                            }
                            
                            Spacer(Modifier.width(16.dp))
                            
                            // Property specifications & title
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = property.title, 
                                    color = MaterialTheme.colorScheme.onBackground, 
                                    fontWeight = FontWeight.Bold, 
                                    fontSize = 15.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                
                                Spacer(Modifier.height(4.dp))
                                
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Rounded.Bed, 
                                        null, 
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant, 
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        " ${property.bedrooms} Beds", 
                                        color = MaterialTheme.colorScheme.onSurfaceVariant, 
                                        fontSize = 11.sp
                                    )
                                    Spacer(Modifier.width(10.dp))
                                    Icon(
                                        Icons.Rounded.SquareFoot, 
                                        null, 
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant, 
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        " ${property.areaM2}m²", 
                                        color = MaterialTheme.colorScheme.onSurfaceVariant, 
                                        fontSize = 11.sp
                                    )
                                }
                                
                                Spacer(Modifier.height(8.dp))
                                
                                Row(verticalAlignment = Alignment.Bottom) {
                                    Text(
                                        text = "%,d".format(property.pricePerMonth), 
                                        color = MaterialTheme.colorScheme.primary, 
                                        fontWeight = FontWeight.Black, 
                                        fontSize = 17.sp
                                    )
                                    Text(
                                        text = " ETB/mo", 
                                        color = MaterialTheme.colorScheme.onSurfaceVariant, 
                                        fontSize = 9.sp, 
                                        modifier = Modifier.padding(bottom = 2.dp)
                                    )
                                }
                            }
                            
                            Spacer(Modifier.width(8.dp))
                            
                            // Navigate Button
                            Button(
                                onClick = { onPropertyClick(property.id) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                ),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    "View", 
                                    fontSize = 11.sp, 
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
                else -> {
                    // Hint state to help user interact with map badges
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                        shape = RoundedCornerShape(16.dp),
                        shadowElevation = 8.dp,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp), 
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Rounded.TouchApp, 
                                null, 
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(10.dp))
                            Text(
                                "Tap a price badge on the map to view details", 
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PriceBadge(
    modifier: Modifier, 
    price: String, 
    isDark: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.clickable { onClick() },
        color = if (isDark) MaterialTheme.colorScheme.primary else Color.White,
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 6.dp,
        border = if (!isDark) BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.8f)) else null
    ) {
        Text(
            text = price,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            color = if (isDark) Color.White else MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 12.sp
        )
    }
}
