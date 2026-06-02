package com.renteasy.app.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
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
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import coil.compose.AsyncImage
import com.renteasy.app.ui.navigation.AuthViewModel
import com.renteasy.app.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    onNavigateBack: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val currentUser by viewModel.currentUser.collectAsState()
    
    var fullName by remember { mutableStateOf(currentUser?.fullName ?: "") }
    var email by remember { mutableStateOf(currentUser?.email ?: "") }
    var phone by remember { mutableStateOf(currentUser?.phone ?: "") }
    var location by remember { mutableStateOf(currentUser?.location ?: "Addis Ababa, Ethiopia") }
    val scope = rememberCoroutineScope()

    var isSaving by remember { mutableStateOf(false) }
    var isUploadingAvatar by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Update fields when currentUser changes (e.g. after avatar upload refreshes from Firestore)
    LaunchedEffect(currentUser) {
        currentUser?.let {
            fullName = it.fullName
            email = it.email ?: ""
            phone = it.phone
            location = it.location ?: "Addis Ababa, Ethiopia"
        }
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { 
            scope.launch {
                isUploadingAvatar = true
                val result = viewModel.uploadAvatar(it.toString())
                isUploadingAvatar = false
                if (result.isSuccess) {
                    snackbarHostState.showSnackbar("Profile photo updated!")
                } else {
                    snackbarHostState.showSnackbar("Failed to upload photo. Please try again.")
                }
            }
        }
    }

    // ByteArray for base64, String for https://, null means show letter avatar
    // Explicitly typed as Any? so Kotlin uses Any.equals() at the null-check site
    val avatarImageModel: Any? = remember(currentUser?.avatarUrl) {
        val url = currentUser?.avatarUrl
        when {
            url.isNullOrBlank() -> null
            url.startsWith("data:image") -> {
                try {
                    android.util.Base64.decode(url.substringAfter("base64,"), android.util.Base64.DEFAULT)
                } catch (e: Exception) {
                    null // decode failed — letter avatar shown instead
                }
            }
            else -> url
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack, enabled = !isSaving && !isUploadingAvatar) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, null, tint = MaterialTheme.colorScheme.onBackground)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar: letter initial always rendered, real photo overlaid when available
            Box(contentAlignment = Alignment.BottomEnd) {
                Box(contentAlignment = Alignment.Center) {
                    Surface(
                        modifier = Modifier.size(100.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            // Letter initial — always visible as fallback
                            Text(
                                text = (currentUser?.fullName ?: "U").take(1).uppercase(),
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                            // Real photo overlay
                            if (avatarImageModel != null) {
                                AsyncImage(
                                    model = avatarImageModel,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                    if (isUploadingAvatar) {
                        // Overlay spinner while uploading
                        Surface(
                            color = Color.Black.copy(alpha = 0.5f),
                            shape = CircleShape,
                            modifier = Modifier.size(100.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(32.dp), strokeWidth = 3.dp)
                            }
                        }
                    }
                }
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                    modifier = Modifier
                        .size(32.dp)
                        .offset(x = 4.dp, y = 4.dp)
                        .clickable(enabled = !isUploadingAvatar) { launcher.launch("image/*") },
                    border = androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.background)
                ) {
                    Icon(Icons.Rounded.CameraAlt, null, tint = Color.White, modifier = Modifier.padding(6.dp))
                }
            }
            
            if (isUploadingAvatar) {
                Spacer(Modifier.height(8.dp))
                Text("Uploading photo…", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
            }

            Spacer(Modifier.height(32.dp))

            EditField(label = "Full Name", value = fullName, onValueChange = { fullName = it }, icon = Icons.Rounded.Person, enabled = !isSaving)
            Spacer(Modifier.height(16.dp))
            EditField(label = "Email Address", value = email, onValueChange = { email = it }, icon = Icons.Rounded.Email, enabled = false)
            Spacer(Modifier.height(16.dp))
            EditField(label = "Phone Number", value = phone, onValueChange = { phone = it }, icon = Icons.Rounded.Phone, enabled = !isSaving)
            Spacer(Modifier.height(16.dp))
            EditField(label = "Location", value = location, onValueChange = { location = it }, icon = Icons.Rounded.LocationOn, enabled = !isSaving)

            Spacer(Modifier.weight(1f))

            Button(
                onClick = { 
                    scope.launch {
                        isSaving = true
                        val result = viewModel.updateProfile(fullName, phone, location)
                        isSaving = false
                        if (result.isSuccess) {
                            snackbarHostState.showSnackbar("Profile saved successfully!")
                            onNavigateBack()
                        } else {
                            snackbarHostState.showSnackbar("Failed to save. Please try again.")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(16.dp),
                enabled = !isSaving && !isUploadingAvatar
            ) {
                if (isSaving) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Save Changes", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    enabled: Boolean = true
) {
    Column {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp, modifier = Modifier.padding(bottom = 8.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            leadingIcon = { Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            shape = RoundedCornerShape(12.dp)
        )
    }
}
