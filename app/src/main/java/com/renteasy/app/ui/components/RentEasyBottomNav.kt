package com.renteasy.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.automirrored.rounded.Chat
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.renteasy.app.ui.navigation.Screen
import com.renteasy.app.ui.theme.*

private data class NavItem(
    val screen: Screen,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

@Composable
fun RentEasyBottomNav(
    currentRoute: String?,
    isOwner: Boolean,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val navItems = if (isOwner) {
        listOf(
            NavItem(Screen.OwnerDashboard, "Dashboard", Icons.Rounded.Dashboard, Icons.Outlined.Dashboard),
            NavItem(Screen.Bookings, "Bookings", Icons.Rounded.BookOnline, Icons.Outlined.BookOnline),
            NavItem(Screen.Chat, "Messages", Icons.AutoMirrored.Rounded.Chat, Icons.AutoMirrored.Outlined.Chat),
            NavItem(Screen.Profile, "Profile", Icons.Rounded.Person, Icons.Outlined.Person),
        )
    } else {
        listOf(
            NavItem(Screen.Home, "Home", Icons.Rounded.Home, Icons.Outlined.Home),
            NavItem(Screen.Map, "Map", Icons.Rounded.Map, Icons.Outlined.Map),
            NavItem(Screen.Bookings, "Bookings", Icons.Rounded.BookOnline, Icons.Outlined.BookOnline),
            NavItem(Screen.Profile, "Profile", Icons.Rounded.Person, Icons.Outlined.Person),
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.outline)
                .align(Alignment.TopCenter)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navItems.forEach { item ->
                val selected = currentRoute == item.screen.route
                val iconColor by animateColorAsState(
                    targetValue = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                    label = "navIconColor"
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { 
                            // Special case for chat in bottom nav
                            if (item.screen == Screen.Chat) {
                                onNavigate(Screen.Chat.createRoute("general"))
                            } else {
                                onNavigate(item.screen.route) 
                            }
                        }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label,
                        tint = iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = iconColor,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}
