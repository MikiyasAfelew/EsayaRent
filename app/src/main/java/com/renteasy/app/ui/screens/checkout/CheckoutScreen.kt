package com.renteasy.app.ui.screens.checkout

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.renteasy.app.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.text.style.TextAlign
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebResourceRequest
import androidx.compose.ui.viewinterop.AndroidView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    propertyId: String,
    onBack: () -> Unit,
    viewModel: CheckoutViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(propertyId) {
        viewModel.loadProperty(propertyId)
    }

    LaunchedEffect(uiState.paymentSuccess) {
        if (uiState.paymentSuccess) {
            delay(2500)
            viewModel.resetSuccessState()
            onBack()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Secure Checkout", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack, enabled = !uiState.isProcessing && !uiState.showChapaGateway) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            } else if (uiState.property != null) {
                val property = uiState.property!!

                Column(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(24.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        item {
                            BookingSummaryCard(property.title, property.images.firstOrNull() ?: "")
                        }

                        // --- Customer Billing Details required by Chapa ---
                        item {
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                            ) {
                                Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                    Text("Billing Information", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
                                    
                                    OutlinedTextField(
                                        value = uiState.fullName,
                                        onValueChange = viewModel::updateFullName,
                                        label = { Text("Full Name") },
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    )

                                    OutlinedTextField(
                                        value = uiState.email,
                                        onValueChange = viewModel::updateEmail,
                                        label = { Text("Email Address") },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    )

                                    OutlinedTextField(
                                        value = uiState.phone,
                                        onValueChange = viewModel::updatePhone,
                                        label = { Text("Phone Number") },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                }
                            }
                        }

                        item {
                            CostBreakdownSection(uiState.totalAmount)
                        }

                        item {
                            SecurityBadges()
                        }
                    }

                    // --- Pay Button Section ---
                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.fillMaxWidth().navigationBarsPadding()
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Button(
                                onClick = { viewModel.initiateChapaPayment() },
                                modifier = Modifier.fillMaxWidth().height(64.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = ChapaGreen), // Chapa Theme Green
                                shape = RoundedCornerShape(16.dp),
                                enabled = !uiState.isProcessing && !uiState.paymentSuccess && uiState.fullName.isNotBlank() && uiState.email.isNotBlank()
                            ) {
                                if (uiState.isProcessing) {
                                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                                } else {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Rounded.Payments, contentDescription = null, tint = Color.White)
                                        Spacer(Modifier.width(12.dp))
                                        Text("Pay with Chapa", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                                    }
                                }
                            }
                            
                            if (uiState.error != null) {
                                Text(
                                    text = uiState.error!!,
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            // --- Real-time Interactive Chapa Gateway Simulator Overlay ---
            AnimatedVisibility(
                visible = uiState.showChapaGateway,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                ChapaGatewayOverlay(
                    amount = uiState.totalAmount,
                    email = uiState.email,
                    fullName = uiState.fullName,
                    uiState = uiState,
                    viewModel = viewModel,
                    onCancel = { viewModel.finalizeBookingStatus(false) },
                    onAuthorize = { viewModel.finalizeBookingStatus(true) }
                )
            }

            // --- Payment Success Overlay ---
            AnimatedVisibility(
                visible = uiState.paymentSuccess,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background.copy(alpha = 0.95f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Surface(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = CircleShape,
                            modifier = Modifier.size(100.dp)
                        ) {
                            Icon(Icons.Rounded.CheckCircle, null, tint = VerifiedGreen, modifier = Modifier.padding(20.dp))
                        }
                        Spacer(Modifier.height(24.dp))
                        Text("Payment Successful", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
                        Text("Your booking has been created and verified!", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

@Composable
fun BookingSummaryCard(title: String, imageUrl: String) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            com.renteasy.app.ui.components.RentEasyImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier.size(80.dp).clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(16.dp))
            Column {
                Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, maxLines = 1)
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Rounded.CalendarToday, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Monthly Stay", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
fun CostBreakdownSection(amount: Int) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            CostRow("Rent per Month", "$amount ETB")
            CostRow("Service Fee (Admin)", "0 ETB")
            HorizontalDivider(color = MaterialTheme.colorScheme.outline, modifier = Modifier.padding(vertical = 12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Total Amount", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
                Text("$amount ETB", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun CostRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
        Text(value, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SecurityBadges() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Icon(Icons.Rounded.Security, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(8.dp))
        Text("SECURED BY CHAPA ETHIOPIA", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

// --- Stunning, Interactive Chapa Portal Overlay ---
@Composable
fun ChapaGatewayOverlay(
    amount: Int,
    email: String,
    fullName: String,
    uiState: CheckoutUiState,
    viewModel: CheckoutViewModel,
    onCancel: () -> Unit,
    onAuthorize: () -> Unit
) {
    val scope = rememberCoroutineScope()

    var isProcessingStep by remember { mutableStateOf(false) }
    var countdownSeconds by remember { mutableStateOf(60) }

    // Dynamic timer for OTP countdown
    LaunchedEffect(uiState.activePaymentStep) {
        if (uiState.activePaymentStep == "TELEBIRR_OTP") {
            countdownSeconds = 60
            while (countdownSeconds > 0) {
                delay(1000)
                countdownSeconds--
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f),
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC))
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(GatewayDarkBg)
                        .padding(horizontal = 20.dp, vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (uiState.activePaymentStep != "SELECT_METHOD" && !isProcessingStep) {
                            IconButton(onClick = { viewModel.updateActivePaymentStep("SELECT_METHOD") }) {
                                Icon(Icons.AutoMirrored.Rounded.ArrowBack, null, tint = Color.White)
                            }
                            Spacer(Modifier.width(8.dp))
                        }
                        Column {
                            Text("Chapa Checkout", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            Text("Securing payment for RentEasy", color = Color(0xFF94A3B8), fontSize = 12.sp)
                        }
                    }
                    IconButton(onClick = onCancel, enabled = !isProcessingStep) {
                        Icon(Icons.Rounded.Close, null, tint = Color.White)
                    }
                }

                if (uiState.isChapaSandboxApiMode) {
                    var webProgress by remember { mutableStateOf(0f) }
                    var isWebLoading by remember { mutableStateOf(true) }
                    Column(modifier = Modifier.weight(1f).fillMaxWidth()) {
                        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                            AndroidView(
                                factory = { context ->
                                    WebView(context).apply {
                                        settings.javaScriptEnabled = true
                                        settings.domStorageEnabled = true
                                        settings.loadWithOverviewMode = true
                                        settings.useWideViewPort = true
                                        settings.builtInZoomControls = true
                                        settings.displayZoomControls = false
                                        // Use standard mobile Chrome user agent to prevent payment gateways from blocking the WebView
                                        settings.userAgentString = "Mozilla/5.0 (Linux; Android 13; SM-S901B) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Mobile Safari/537.36"
                                        
                                        webViewClient = object : WebViewClient() {
                                            override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                                                isWebLoading = true
                                                val checkedUrl = url ?: ""
                                                android.util.Log.d("ChapaWebView", "onPageStarted: $checkedUrl")
                                            }

                                            override fun onPageFinished(view: WebView?, url: String?) {
                                                isWebLoading = false
                                                val mainUrl = view?.url ?: ""
                                                android.util.Log.d("ChapaWebView", "onPageFinished - url: $url, mainUrl: $mainUrl")
                                                try {
                                                    val uri = android.net.Uri.parse(mainUrl)
                                                    if (uri.host == "renteasy.app" && uri.path == "/payment-success") {
                                                        android.util.Log.d("ChapaWebView", "onPageFinished INTERCEPTED SUCCESS!")
                                                        viewModel.finalizeBookingStatus(true)
                                                    }
                                                } catch (e: Exception) {
                                                    // Handle parse exception gracefully
                                                }
                                            }

                                            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                                                val checkedUrl = request?.url?.toString() ?: ""
                                                val isMainFrame = request?.isForMainFrame ?: false
                                                android.util.Log.d("ChapaWebView", "shouldOverrideUrlLoading: $checkedUrl, isMainFrame: $isMainFrame")
                                                try {
                                                    val uri = android.net.Uri.parse(checkedUrl)
                                                    if (uri.host == "renteasy.app" && uri.path == "/payment-success") {
                                                        android.util.Log.d("ChapaWebView", "shouldOverrideUrlLoading INTERCEPTED SUCCESS!")
                                                        viewModel.finalizeBookingStatus(true)
                                                        return true // Block navigation immediately to avoid "could not be loaded" error screen
                                                    }
                                                } catch (e: Exception) {
                                                    // Handle parse exception gracefully
                                                }
                                                return false
                                            }
                                        }
                                        webChromeClient = object : android.webkit.WebChromeClient() {
                                            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                                                webProgress = newProgress / 100f
                                            }
                                        }
                                        loadUrl(uiState.checkoutUrl)
                                    }
                                },
                                update = { _ ->
                                    // Keep webView loaded with latest url
                                },
                                modifier = Modifier.fillMaxSize()
                            )

                            if (isWebLoading) {
                                LinearProgressIndicator(
                                    progress = { webProgress },
                                    modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter),
                                    color = ChapaGreen,
                                    trackColor = Color(0xFFE2E8F0)
                                )
                            }
                        }

                        // Beautiful manual completion fallback button
                        Surface(
                            color = Color(0xFFF8FAFC),
                            modifier = Modifier.fillMaxWidth(),
                            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                        ) {
                            Button(
                                onClick = onAuthorize,
                                colors = ButtonDefaults.buttonColors(containerColor = ChapaGreen),
                                modifier = Modifier.fillMaxWidth().padding(16.dp).height(54.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("I Have Completed the Payment", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            }
                        }
                    }
                } else if (isProcessingStep) {
                    // --- Custom animated transaction loader ---
                    Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(24.dp)
                        ) {
                            CircularProgressIndicator(
                                color = ChapaGreen,
                                strokeWidth = 5.dp,
                                modifier = Modifier.size(72.dp)
                            )
                            Spacer(Modifier.height(32.dp))
                            Text(
                                text = uiState.simulatedProgressMessage,
                                style = MaterialTheme.typography.titleMedium,
                                color = GatewayDarkBg,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Do not close this window or refresh the gateway.",
                                style = MaterialTheme.typography.bodySmall,
                                color = GatewayTextMuted
                            )
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(24.dp)
                    ) {
                        // Payable Summary
                        Surface(
                            color = GatewayDarkBg.copy(alpha = 0.03f),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("Payment to RentEasy", color = GatewayTextMuted, fontSize = 12.sp)
                                    Text(email, color = GatewayDarkBg, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                }
                                Text("${amount} ETB", color = ChapaGreen, fontWeight = FontWeight.Black, fontSize = 24.sp)
                            }
                        }

                        Spacer(Modifier.height(24.dp))

                        // Dynamic Screen rendering
                        when (uiState.activePaymentStep) {
                            "SELECT_METHOD" -> {
                                Text("Select Payment Method", color = GatewayDarkBg, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Spacer(Modifier.height(16.dp))
                                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                    ChapaMethodRow(
                                        title = "Telebirr Mobile Money",
                                        subtitle = "Instant payment using Ethio Telecom account",
                                        iconText = "Telebirr",
                                        iconBg = TelebirrBlue,
                                        onClick = { 
                                            viewModel.updatePhoneInput(uiState.phone.ifBlank { "09" })
                                            viewModel.updateActivePaymentStep("TELEBIRR_INPUT")
                                        }
                                    )
                                    ChapaMethodRow(
                                        title = "CBE Birr Portal",
                                        subtitle = "Commercial Bank of Ethiopia smart portal",
                                        iconText = "CBE Birr",
                                        iconBg = CBEPurple,
                                        onClick = { 
                                            viewModel.updatePhoneInput(uiState.phone.ifBlank { "09" })
                                            viewModel.updateActivePaymentStep("CBE_INPUT")
                                        }
                                    )
                                    ChapaMethodRow(
                                        title = "Credit or Debit Card",
                                        subtitle = "Visa, MasterCard, American Express, UnionPay",
                                        iconText = "Card",
                                        iconBg = GatewayDarkBg,
                                        onClick = { 
                                            viewModel.updateCardName(fullName)
                                            viewModel.updateActivePaymentStep("CARD_INPUT")
                                        }
                                    )
                                }
                            }

                            "TELEBIRR_INPUT" -> {
                                // Branded Telebirr Layout
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(color = TelebirrBlue, shape = RoundedCornerShape(8.dp), modifier = Modifier.size(36.dp)) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(Icons.Rounded.PhoneAndroid, null, tint = Color.White, modifier = Modifier.size(20.dp))
                                        }
                                    }
                                    Spacer(Modifier.width(12.dp))
                                    Text("Telebirr Mobile Money", color = GatewayDarkBg, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                }
                                Spacer(Modifier.height(20.dp))
                                Text(
                                    "Enter your Telebirr registered phone number. We will send an SMS OTP verification code to confirm.",
                                    color = GatewayTextBody,
                                    fontSize = 14.sp
                                )
                                Spacer(Modifier.height(20.dp))
                                OutlinedTextField(
                                    value = uiState.phoneInput,
                                    onValueChange = viewModel::updatePhoneInput,
                                    label = { Text("Telebirr Phone Number") },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = TelebirrBlue,
                                        unfocusedBorderColor = Color(0xFFCBD5E1)
                                    )
                                )
                                Spacer(Modifier.weight(1f))
                                Button(
                                    onClick = {
                                        scope.launch {
                                            isProcessingStep = true
                                            viewModel.updateSimulatedProgressMessage("Sending OTP to your device...")
                                            delay(1500)
                                            isProcessingStep = false
                                            viewModel.updateActivePaymentStep("TELEBIRR_OTP")
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth().height(56.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = TelebirrBlue),
                                    shape = RoundedCornerShape(12.dp),
                                    enabled = uiState.phoneInput.length >= 9
                                ) {
                                    Text("Proceed to OTP", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                }
                            }

                            "TELEBIRR_OTP" -> {
                                // OTP Code Screen
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(color = TelebirrBlue, shape = RoundedCornerShape(8.dp), modifier = Modifier.size(36.dp)) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(Icons.Rounded.Sms, null, tint = Color.White, modifier = Modifier.size(20.dp))
                                        }
                                    }
                                    Spacer(Modifier.width(12.dp))
                                    Text("SMS Verification", color = GatewayDarkBg, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                }
                                Spacer(Modifier.height(16.dp))
                                Text(
                                    "We sent a 6-digit verification code to ${uiState.phoneInput}. Please enter it below.",
                                    color = GatewayTextBody,
                                    fontSize = 14.sp
                                )
                                Spacer(Modifier.height(20.dp))
                                
                                OutlinedTextField(
                                    value = uiState.otpInput,
                                    onValueChange = { if (it.length <= 6) viewModel.updateOtpInput(it) },
                                    label = { Text("6-Digit OTP Code") },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = TelebirrBlue,
                                        unfocusedBorderColor = Color(0xFFCBD5E1)
                                    ),
                                    trailingIcon = {
                                        Row(
                                            modifier = Modifier.padding(end = 12.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(Icons.Rounded.Timer, null, tint = GatewayTextMuted, modifier = Modifier.size(16.dp))
                                            Spacer(Modifier.width(4.dp))
                                            Text("${countdownSeconds}s", color = GatewayTextMuted, fontSize = 13.sp)
                                        }
                                    }
                                )
                                
                                Spacer(Modifier.weight(1f))
                                Button(
                                    onClick = {
                                        scope.launch {
                                            isProcessingStep = true
                                            viewModel.updateSimulatedProgressMessage("Verifying OTP code...")
                                            delay(1500)
                                            viewModel.updateSimulatedProgressMessage("Connecting with Telebirr Core...")
                                            delay(1500)
                                            viewModel.updateSimulatedProgressMessage("Processing secure payment...")
                                            delay(1500)
                                            isProcessingStep = false
                                            onAuthorize()
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth().height(56.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = ChapaGreen),
                                    shape = RoundedCornerShape(12.dp),
                                    enabled = uiState.otpInput.length == 6
                                ) {
                                    Text("Confirm & Authorize Payment", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                }
                            }

                            "CBE_INPUT" -> {
                                // Branded CBE birr
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(color = CBEPurple, shape = RoundedCornerShape(8.dp), modifier = Modifier.size(36.dp)) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(Icons.Rounded.AccountBalance, null, tint = Color.White, modifier = Modifier.size(20.dp))
                                        }
                                    }
                                    Spacer(Modifier.width(12.dp))
                                    Text("CBE Birr Smart Portal", color = GatewayDarkBg, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                }
                                Spacer(Modifier.height(20.dp))
                                Text(
                                    "Enter your CBE Birr mobile money registered phone number. A secure USSD confirmation push will be sent to your device.",
                                    color = GatewayTextBody,
                                    fontSize = 14.sp
                                )
                                Spacer(Modifier.height(20.dp))
                                OutlinedTextField(
                                    value = uiState.phoneInput,
                                    onValueChange = viewModel::updatePhoneInput,
                                    label = { Text("CBE Birr Registered Phone") },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = CBEPurple,
                                        unfocusedBorderColor = Color(0xFFCBD5E1)
                                    )
                                )
                                Spacer(Modifier.weight(1f))
                                Button(
                                    onClick = {
                                        scope.launch {
                                            isProcessingStep = true
                                            viewModel.updateSimulatedProgressMessage("Sending USSD Push to your phone...")
                                            delay(2000)
                                            viewModel.updateSimulatedProgressMessage("Waiting for user PIN confirmation...")
                                            delay(2500)
                                            viewModel.updateSimulatedProgressMessage("Securing bank transfer...")
                                            delay(2000)
                                            isProcessingStep = false
                                            onAuthorize()
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth().height(56.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = CBEPurple),
                                    shape = RoundedCornerShape(12.dp),
                                    enabled = uiState.phoneInput.length >= 9
                                ) {
                                    Text("Request USSD Confirmation", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                }
                            }

                            "CARD_INPUT" -> {
                                // Stunning Credit Card Form with interactive graphics card representation
                                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                    // Interactive Card Representation
                                    InteractiveCardGraphic(
                                        num = uiState.cardNumber,
                                        expiry = uiState.cardExpiry,
                                        cvv = uiState.cardCvv,
                                        name = uiState.cardName
                                    )

                                    OutlinedTextField(
                                        value = uiState.cardNumber,
                                        onValueChange = viewModel::updateCardNumber,
                                        label = { Text("Card Number") },
                                        placeholder = { Text("4000 1234 5678 9010") },
                                        modifier = Modifier.fillMaxWidth(),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        shape = RoundedCornerShape(12.dp)
                                    )

                                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                        OutlinedTextField(
                                            value = uiState.cardExpiry,
                                            onValueChange = viewModel::updateCardExpiry,
                                            label = { Text("Expiry (MM/YY)") },
                                            placeholder = { Text("12/28") },
                                            modifier = Modifier.weight(1f),
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        OutlinedTextField(
                                            value = uiState.cardCvv,
                                            onValueChange = viewModel::updateCardCvv,
                                            label = { Text("CVV") },
                                            placeholder = { Text("123") },
                                            modifier = Modifier.weight(1f),
                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                    }

                                    OutlinedTextField(
                                        value = uiState.cardName,
                                        onValueChange = viewModel::updateCardName,
                                        label = { Text("Cardholder Name") },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp)
                                    )

                                    Spacer(Modifier.weight(1f))
                                    Button(
                                        onClick = {
                                            viewModel.updateActivePaymentStep("CARD_3DS")
                                        },
                                        modifier = Modifier.fillMaxWidth().height(56.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = ChapaGreen),
                                        shape = RoundedCornerShape(12.dp),
                                        enabled = uiState.cardNumber.length >= 12 && uiState.cardExpiry.length >= 4 && uiState.cardCvv.length >= 3 && uiState.cardName.isNotBlank()
                                    ) {
                                        Text("Proceed Securely", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    }
                                }
                            }

                            "CARD_3DS" -> {
                                // 3D Secure Banking authentication simulator screen
                                Column(
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(20.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("🏦 Verified by Visa / Identity Check", fontWeight = FontWeight.Bold, color = GatewayDarkBg, fontSize = 15.sp)
                                        Icon(Icons.Rounded.Security, null, tint = ChapaGreen, modifier = Modifier.size(24.dp))
                                    }
                                    HorizontalDivider(color = Color(0xFFE2E8F0))
                                    Text(
                                        "Please verify this transaction. We have sent a verification code to the phone number registered with your bank card.",
                                        textAlign = TextAlign.Center,
                                        color = GatewayTextBody,
                                        fontSize = 14.sp
                                    )
                                    
                                    OutlinedTextField(
                                        value = uiState.otpInput,
                                        onValueChange = viewModel::updateOtpInput,
                                        label = { Text("Enter bank authorization code") },
                                        placeholder = { Text("6-digit code") },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        modifier = Modifier.fillMaxWidth(0.8f),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = ChapaGreen
                                        )
                                    )

                                    Spacer(Modifier.weight(1f))
                                    Button(
                                        onClick = {
                                            scope.launch {
                                                isProcessingStep = true
                                                viewModel.updateSimulatedProgressMessage("Contacting card issuer bank...")
                                                delay(1500)
                                                viewModel.updateSimulatedProgressMessage("Authenticating transaction security credentials...")
                                                delay(1500)
                                                viewModel.updateSimulatedProgressMessage("Processing payment with Chapa...")
                                                delay(1500)
                                                isProcessingStep = false
                                                onAuthorize()
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth().height(56.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = ChapaGreen),
                                        shape = RoundedCornerShape(12.dp),
                                        enabled = uiState.otpInput.isNotBlank()
                                    ) {
                                        Text("Verify & Pay", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InteractiveCardGraphic(num: String, expiry: String, cvv: String, name: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(16.dp),
        color = GatewayDarkBg,
        border = BorderStroke(1.dp, Color(0xFF334155))
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(20.dp)) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("PLATINUM PREMIUM", color = Color.LightGray, fontWeight = FontWeight.Black, fontSize = 12.sp)
                    // Simple card brand logo detect
                    val brand = if (num.startsWith("4")) "Visa" else if (num.startsWith("5")) "MasterCard" else "Chapa Pay"
                    Text(brand, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }

                // Chip
                Surface(
                    color = Color(0xFFE2E8F0).copy(alpha = 0.25f),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.size(36.dp, 26.dp)
                ) {}

                // Card Number formatted
                val formattedNum = remember(num) {
                    val raw = num.take(16).padEnd(16, '•')
                    raw.chunked(4).joinToString(" ")
                }
                Text(
                    text = formattedNum,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    letterSpacing = 2.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text("CARD HOLDER", color = Color(0xFF94A3B8), fontSize = 10.sp)
                        Text(name.uppercase().ifBlank { "YOUR NAME" }, color = Color.White, fontWeight = FontWeight.Medium, fontSize = 13.sp, maxLines = 1)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("EXPIRES", color = Color(0xFF94A3B8), fontSize = 10.sp)
                        Text(expiry.ifBlank { "MM/YY" }, color = Color.White, fontWeight = FontWeight.Medium, fontSize = 13.sp)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("CVV", color = Color(0xFF94A3B8), fontSize = 10.sp)
                        Text(cvv.ifBlank { "•••" }, color = Color.White, fontWeight = FontWeight.Medium, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ChapaMethodRow(
    title: String,
    subtitle: String,
    iconText: String,
    iconBg: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        border = BorderStroke(1.5.dp, Color(0xFFCBD5E1)),
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(color = iconBg, shape = RoundedCornerShape(8.dp), modifier = Modifier.size(44.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Text(iconText.take(6), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 9.sp, maxLines = 1)
                }
            }
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = GatewayDarkBg, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(subtitle, color = GatewayTextMuted, fontSize = 11.sp)
            }
            Icon(Icons.AutoMirrored.Rounded.ArrowForward, null, tint = GatewayTextMuted)
        }
    }
}
