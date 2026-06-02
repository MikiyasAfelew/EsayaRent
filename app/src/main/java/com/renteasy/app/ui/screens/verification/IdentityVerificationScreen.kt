package com.renteasy.app.ui.screens.verification

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.renteasy.app.domain.model.UserVerificationLevel
import com.renteasy.app.ui.navigation.AuthViewModel
import com.renteasy.app.ui.theme.*
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IdentityVerificationScreen(
    onBack: () -> Unit,
    onVerificationSubmitted: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val currentUser by viewModel.currentUser.collectAsState()
    val isOwner = currentUser?.isOwner == true
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // --- Step state (0=Info, 1=Documents, 2=Review) ---
    var currentStep by remember { mutableStateOf(0) }
    var isSubmitting by remember { mutableStateOf(false) }

    // --- Document image URIs ---
    var idFrontUri by remember { mutableStateOf<Uri?>(null) }
    var idBackUri by remember { mutableStateOf<Uri?>(null) }
    var ownershipUri by remember { mutableStateOf<Uri?>(null) }
    var licenseUri by remember { mutableStateOf<Uri?>(null) }
    var selfieUri by remember { mutableStateOf<Uri?>(null) }

    // Track which slot is being picked
    var pickingSlot by remember { mutableStateOf("") }

    // Camera temp file URI — created safely
    val cameraUri = remember {
        try {
            val file = File(context.cacheDir, "liveness_selfie.jpg")
            if (!file.exists()) file.createNewFile()
            FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        } catch (e: Exception) {
            null
        }
    }

    // --- Launchers ---
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) when (pickingSlot) {
            "id_front" -> idFrontUri = uri
            "id_back" -> idBackUri = uri
            "selfie" -> selfieUri = uri
            "ownership" -> ownershipUri = uri
            "license" -> licenseUri = uri
        }
        pickingSlot = ""
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && cameraUri != null) selfieUri = cameraUri
    }

    // --- Computed progress ---
    val completedDocs = listOfNotNull(idFrontUri, idBackUri, selfieUri,
        if (isOwner) ownershipUri else Uri.EMPTY).count { it != Uri.EMPTY }
    val totalRequired = if (isOwner) 4 else 3
    val docsProgress = completedDocs.toFloat() / totalRequired
    val stepProgress = when (currentStep) {
        0 -> 0.33f
        1 -> 0.33f + (docsProgress * 0.34f)
        else -> 1f
    }
    val animatedProgress by animateFloatAsState(targetValue = stepProgress, animationSpec = tween(600), label = "progress")

    val stepLabels = listOf("Personal Info", "Documents", "Review & Submit")
    val screenTitle = when (currentStep) {
        0 -> if (isOwner) "Owner Verification" else "Renter Verification"
        1 -> "Upload Documents"
        else -> "Final Review"
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(screenTitle, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onBackground) },
                navigationIcon = {
                    IconButton(onClick = { if (currentStep > 0) currentStep-- else onBack() }, enabled = !isSubmitting) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onBackground)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {

            // ── Step Progress Bar ──────────────────────────────────────────────
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        "STEP ${currentStep + 1} OF 3",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        "${(animatedProgress * 100).toInt()}% Complete",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surface
                )
                Spacer(Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    stepLabels.forEachIndexed { i, label ->
                        Text(
                            label,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (i == currentStep) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = if (i == currentStep) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            // ── Step Content ──────────────────────────────────────────────────
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                when (currentStep) {
                    // ─── STEP 0: Why Verify / Info ────────────────────────────
                    0 -> {
                        item {
                            Surface(
                                color = if (isOwner) Color(0xFF1A2744) else Color(0xFF1E1B4B),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Surface(shape = CircleShape, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), modifier = Modifier.size(72.dp)) {
                                        Icon(
                                            if (isOwner) Icons.Outlined.HomeWork else Icons.Outlined.Shield,
                                            null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.padding(16.dp)
                                        )
                                    }
                                    Spacer(Modifier.height(16.dp))
                                    Text(
                                        if (isOwner) "Unlock Property Listing" else "Secure Your Booking",
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        if (isOwner)
                                            "To list properties on RentEasy, owners must verify their identity and submit ownership documents. All listings are approved by our admin team before going live."
                                        else
                                            "Verification lets you book properties, sign digital leases, and chat securely with owners. Your data is encrypted and never shared.",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                        item {
                            Text("What you'll need", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
                        }
                        item { InfoRequirementRow(Icons.Outlined.Badge, "National ID, Passport, or Kebele ID (front & back)") }
                        item { InfoRequirementRow(Icons.Outlined.Face, "A live selfie / liveness photo") }
                        if (isOwner) {
                            item { InfoRequirementRow(Icons.Outlined.Description, "Property ownership proof (title deed or lease agreement) — Required") }
                            item { InfoRequirementRow(Icons.Outlined.Work, "Business / Commercial license — Optional") }
                        }
                    }

                    // ─── STEP 1: Document Uploads ─────────────────────────────
                    1 -> {
                        item {
                            Text(
                                "Upload your documents",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "Tap any slot to choose a photo from your gallery.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // National ID card
                        item {
                            DocumentCardWithPicker(
                                title = "National ID / Passport / Kebele ID",
                                isRequired = true,
                                icon = Icons.Outlined.Badge,
                                slots = listOf(
                                    "Front Side" to idFrontUri,
                                    "Back Side" to idBackUri
                                ),
                                onSlotClick = { slotLabel ->
                                    // Map display labels to storage keys
                                    pickingSlot = when (slotLabel) {
                                        "Front Side" -> "id_front"
                                        "Back Side" -> "id_back"
                                        else -> slotLabel
                                    }
                                    galleryLauncher.launch("image/*")
                                }
                            )
                        }

                        // Liveness selfie card
                        item {
                            LivenessCardFunctional(
                                selfieUri = selfieUri,
                                onTakePhoto = {
                                    if (cameraUri != null) {
                                        cameraLauncher.launch(cameraUri)
                                    } else {
                                        // Fallback to gallery if camera URI failed
                                        pickingSlot = "selfie"
                                        galleryLauncher.launch("image/*")
                                    }
                                },
                                onPickFromGallery = {
                                    pickingSlot = "selfie"
                                    galleryLauncher.launch("image/*")
                                },
                                onRetake = { selfieUri = null }
                            )
                        }

                        // Owner-only documents
                        if (isOwner) {
                            item {
                                DocumentCardWithPicker(
                                    title = "Property Ownership Proof",
                                    isRequired = true,
                                    icon = Icons.Outlined.Description,
                                    slots = listOf("Title Deed / Lease Agreement" to ownershipUri),
                                    onSlotClick = {
                                        pickingSlot = "ownership"
                                        galleryLauncher.launch("image/*")
                                    }
                                )
                            }
                            item {
                                DocumentCardWithPicker(
                                    title = "Business / Commercial License",
                                    isRequired = false,
                                    icon = Icons.Outlined.Work,
                                    slots = listOf("Upload license document" to licenseUri),
                                    onSlotClick = {
                                        pickingSlot = "license"
                                        galleryLauncher.launch("image/*")
                                    }
                                )
                            }
                        }

                        // Telebirr optional link
                        item { TelebirrQuickVerify() }
                    }

                    // ─── STEP 2: Review ───────────────────────────────────────
                    2 -> {
                        item {
                            Text("Review & Submit", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
                            Text(
                                if (isOwner) "Your documents will be reviewed by our admin team within 24 hours." else "Your identity documents are ready to submit.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        item { ReviewRow("National ID — Front", idFrontUri) }
                        item { ReviewRow("National ID — Back", idBackUri) }
                        item { ReviewRow("Liveness Selfie", selfieUri) }
                        if (isOwner) {
                            item { ReviewRow("Ownership Proof", ownershipUri) }
                            item { ReviewRow("Business License (optional)", licenseUri) }
                        }
                        item {
                            Surface(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Outlined.Lock, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                                    Spacer(Modifier.width(12.dp))
                                    Text(
                                        "Your documents are encrypted end-to-end and only reviewed by authorized RentEasy staff.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // ── Footer Buttons ────────────────────────────────────────────────
            val requiredComplete = idFrontUri != null && idBackUri != null && selfieUri != null &&
                (!isOwner || ownershipUri != null)

            Row(
                modifier = Modifier.fillMaxWidth().padding(20.dp).navigationBarsPadding(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (currentStep == 0) {
                    OutlinedButton(
                        onClick = onBack,
                        modifier = Modifier.weight(1f).height(54.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        shape = RoundedCornerShape(12.dp)
                    ) { Text("Cancel", color = MaterialTheme.colorScheme.onBackground) }
                }

                Button(
                    onClick = {
                        when (currentStep) {
                            0 -> currentStep = 1
                            1 -> if (requiredComplete) currentStep = 2
                            else -> scope.launch {
                                isSubmitting = true
                                try {
                                    val uid = viewModel.currentUser.value?.id ?: ""
                                    val storage = FirebaseStorage.getInstance()
                                    val firestore = FirebaseFirestore.getInstance()

                                                                         // Helper: upload one URI to Storage, return download URL
                                     suspend fun uploadDoc(uri: Uri?, slot: String): String? {
                                         uri ?: return null
                                         val ref = storage.reference.child("verifications/$uid/$slot.jpg")
                                         try {
                                             val inputStream = context.contentResolver.openInputStream(uri)
                                                 ?: return null
                                             ref.putStream(inputStream).await()
                                             inputStream.close()
                                             return ref.downloadUrl.await().toString()
                                         } catch (storageError: Exception) {
                                             android.util.Log.w("VerificationUpload", "Storage upload failed for " + slot + ", using memory-safe Base64 fallback: " + storageError.message)
                                             try {
                                                 val maxDimension = 400
                                                 val resolver = context.contentResolver
                                                 val options = android.graphics.BitmapFactory.Options().apply {
                                                     inJustDecodeBounds = true
                                                 }
                                                 resolver.openInputStream(uri)?.use { input ->
                                                     android.graphics.BitmapFactory.decodeStream(input, null, options)
                                                 }
                                                 var inSampleSize = 1
                                                 val srcWidth = options.outWidth
                                                 val srcHeight = options.outHeight
                                                 if (srcWidth > maxDimension || srcHeight > maxDimension) {
                                                     val halfWidth = srcWidth / 2
                                                     val halfHeight = srcHeight / 2
                                                     while ((halfWidth / inSampleSize) >= maxDimension && (halfHeight / inSampleSize) >= maxDimension) {
                                                         inSampleSize *= 2
                                                     }
                                                 }
                                                 val decodeOptions = android.graphics.BitmapFactory.Options().apply {
                                                     this.inSampleSize = inSampleSize
                                                 }
                                                 val bitmap = resolver.openInputStream(uri)?.use { input ->
                                                     android.graphics.BitmapFactory.decodeStream(input, null, decodeOptions)
                                                 } ?: return null
                                                 val scaledBitmap = if (bitmap.width > maxDimension || bitmap.height > maxDimension) {
                                                     val scale = maxDimension.toFloat() / maxOf(bitmap.width, bitmap.height)
                                                     android.graphics.Bitmap.createScaledBitmap(
                                                         bitmap,
                                                         (bitmap.width * scale).toInt(),
                                                         (bitmap.height * scale).toInt(),
                                                         true
                                                     )
                                                 } else bitmap
                                                 val baos = java.io.ByteArrayOutputStream()
                                                 scaledBitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 50, baos)
                                                 val base64 = android.util.Base64.encodeToString(baos.toByteArray(), android.util.Base64.NO_WRAP)
                                                 return "data:image/jpeg;base64," + base64
                                             } catch (fallbackError: Exception) {
                                                 android.util.Log.e("VerificationUpload", "Base64 fallback failed for " + slot + ": " + fallbackError.message, fallbackError)
                                                 return null
                                             }
                                         }
                                     }
                                    val idFrontUrl  = uploadDoc(idFrontUri,  "id_front")
                                    val idBackUrl   = uploadDoc(idBackUri,   "id_back")
                                    val selfieUrl   = uploadDoc(selfieUri,   "selfie")
                                    val ownerUrl    = if (isOwner) uploadDoc(ownershipUri, "ownership_proof") else null
                                    val licenseUrl  = if (isOwner) uploadDoc(licenseUri,  "business_license") else null

                                    // Write metadata to Firestore verifications/{uid}
                                    val meta = mutableMapOf<String, Any>(
                                        "userId"       to uid,
                                        "userName"     to (viewModel.currentUser.value?.fullName ?: ""),
                                        "userEmail"    to (viewModel.currentUser.value?.email ?: ""),
                                        "isOwner"      to isOwner,
                                        "status"       to "PENDING_REVIEW",
                                        "submittedAt"  to System.currentTimeMillis()
                                    )
                                    idFrontUrl?.let  { meta["idFrontUrl"]       = it }
                                    idBackUrl?.let   { meta["idBackUrl"]        = it }
                                    selfieUrl?.let   { meta["selfieUrl"]        = it }
                                    ownerUrl?.let    { meta["ownershipProofUrl"] = it }
                                    licenseUrl?.let  { meta["businessLicenseUrl"] = it }

                                    firestore.collection("verifications").document(uid).set(meta).await()

                                    // Update user verification level
                                    viewModel.updateVerificationLevel(UserVerificationLevel.PENDING_REVIEW)
                                } catch (e: Exception) {
                                    android.util.Log.e("VerificationUpload", "Upload failed", e)
                                }
                                isSubmitting = false
                                onVerificationSubmitted()
                            }
                        }
                    },
                    modifier = Modifier.weight(1f).height(54.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !isSubmitting && (currentStep != 1 || requiredComplete)
                ) {
                    if (isSubmitting) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text(
                            when (currentStep) {
                                0 -> "Continue"
                                1 -> if (requiredComplete) "Review Submission" else "Upload Required Docs"
                                else -> if (isOwner) "Submit for Admin Review" else "Submit Verification"
                            },
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

// ── Helper Composables ─────────────────────────────────────────────────────────

@Composable
private fun InfoRequirementRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Surface(shape = CircleShape, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f), modifier = Modifier.size(36.dp)) {
            Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(8.dp))
        }
        Spacer(Modifier.width(12.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun DocumentCardWithPicker(
    title: String,
    isRequired: Boolean,
    icon: ImageVector,
    slots: List<Pair<String, Uri?>>,
    onSlotClick: (String) -> Unit
) {
    Surface(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(title, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                }
                Surface(
                    color = if (isRequired) Color(0xFF450A0A) else MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        if (isRequired) "Required" else "Optional",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isRequired) Color(0xFFFCA5A5) else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                slots.forEach { (label, uri) ->
                    val uploaded = uri != null
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(110.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(
                                1.dp,
                                if (uploaded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                                RoundedCornerShape(12.dp)
                            )
                            .background(
                                if (uploaded) MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
                                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                            )
                            .clickable { onSlotClick(label) },
                        contentAlignment = Alignment.Center
                    ) {
                        if (uploaded) {
                            AsyncImage(model = uri, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)))
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
                                Surface(shape = CircleShape, color = VerifiedGreen, modifier = Modifier.padding(6.dp).size(20.dp)) {
                                    Icon(Icons.Rounded.Check, null, tint = Color.White, modifier = Modifier.padding(4.dp))
                                }
                            }
                        } else {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Outlined.AddAPhoto, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(28.dp))
                                Spacer(Modifier.height(6.dp))
                                Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 4.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LivenessCardFunctional(selfieUri: Uri?, onTakePhoto: () -> Unit, onPickFromGallery: () -> Unit, onRetake: () -> Unit) {
    Surface(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Rounded.Face, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("Liveness Selfie", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                Spacer(Modifier.weight(1f))
                Surface(color = Color(0xFF450A0A), shape = RoundedCornerShape(4.dp)) {
                    Text("Required", modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, color = Color(0xFFFCA5A5))
                }
            }
            Spacer(Modifier.height(12.dp))
            if (selfieUri != null) {
                Box(modifier = Modifier.fillMaxWidth().height(180.dp).clip(RoundedCornerShape(12.dp))) {
                    AsyncImage(model = selfieUri, contentDescription = "Selfie", contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
                        Surface(shape = CircleShape, color = VerifiedGreen, modifier = Modifier.padding(8.dp).size(24.dp)) {
                            Icon(Icons.Rounded.Check, null, tint = Color.White, modifier = Modifier.padding(4.dp))
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))
                OutlinedButton(onClick = onRetake, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)) {
                    Icon(Icons.Outlined.CameraAlt, null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Retake Selfie")
                }
            } else {
                Text("Take a live selfie or pick from your gallery.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = onTakePhoto,
                        modifier = Modifier.weight(1f).height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Outlined.CameraAlt, null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Camera", fontWeight = FontWeight.SemiBold)
                    }
                    OutlinedButton(
                        onClick = onPickFromGallery,
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Outlined.Image, null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Gallery")
                    }
                }
            }
        }
    }
}

@Composable
private fun ReviewRow(label: String, uri: Uri?) {
    val uploaded = uri != null
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            val iconColor by animateColorAsState(
                targetValue = if (uploaded) VerifiedGreen else MaterialTheme.colorScheme.onSurfaceVariant,
                label = "iconColor"
            )
            Icon(
                if (uploaded) Icons.Rounded.CheckCircle else Icons.Rounded.RadioButtonUnchecked,
                null,
                tint = iconColor,
                modifier = Modifier.size(22.dp)
            )
            Spacer(Modifier.width(12.dp))
            Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.weight(1f))
            if (uploaded) {
                AsyncImage(model = uri, contentDescription = null, contentScale = ContentScale.Crop,
                    modifier = Modifier.size(40.dp).clip(RoundedCornerShape(6.dp)))
            }
        }
    }
}

@Composable
fun TelebirrQuickVerify() {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().border(1.dp, TelebirrBlue.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(color = TelebirrBlue, shape = RoundedCornerShape(8.dp), modifier = Modifier.size(44.dp)) {
                Text("t", modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            }
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Verified Telebirr Account", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                Text("Link your Telebirr for faster verification & automated payments.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(Icons.Rounded.ChevronRight, null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
