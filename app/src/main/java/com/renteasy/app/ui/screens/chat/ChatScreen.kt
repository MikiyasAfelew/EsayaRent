package com.renteasy.app.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.renteasy.app.ui.theme.*
import kotlinx.coroutines.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.renteasy.app.ui.navigation.AuthViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

data class Message(
    val id: String,
    val text: String,
    val time: String,
    val isFromMe: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    ownerId: String,
    ownerName: String,
    propertyTitle: String,
    propertyPrice: String,
    propertyImage: String,
    onBack: () -> Unit,
    onNavigateToChat: (String, String, String, String, String) -> Unit = { _, _, _, _, _ -> },
    authViewModel: AuthViewModel = hiltViewModel()
) {
    if (ownerId == "general") {
        ChatListScreen(
            onBack = onBack,
            onNavigateToChat = onNavigateToChat,
            authViewModel = authViewModel
        )
        return
    }

    var messageText by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    var isOwnerTyping by remember { mutableStateOf(false) }

    val currentUser by authViewModel.currentUser.collectAsState(initial = null)
    val firstName = remember(ownerName) { ownerName.substringBefore(" ") }
    
    val chatId = remember(currentUser, ownerId) {
        val renterId = currentUser?.id ?: "guest"
        if (renterId < ownerId) "${renterId}_${ownerId}" else "${ownerId}_${renterId}"
    }

    val firestore = remember { FirebaseFirestore.getInstance() }
    val messages = remember { mutableStateListOf<Message>() }

    DisposableEffect(chatId) {
        val subscription = firestore.collection("chats").document(chatId).collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    error.printStackTrace()
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    messages.clear()
                    if (snapshot.isEmpty) {
                        val initial = listOf(
                            Message("1", "Hello! Thank you for your interest in $propertyTitle. It's currently available for viewing.", "10:24 AM", false),
                            Message("2", "Yes, we can definitely discuss the leasing details. All my properties are verified through RentEasy's legal team for your security.", "10:28 AM", false)
                        )
                        messages.addAll(initial)
                    } else {
                        for (doc in snapshot.documents) {
                            val text = doc.getString("text") ?: ""
                            val senderId = doc.getString("senderId") ?: ""
                            val timestamp = doc.getLong("timestamp") ?: 0L
                            val timeStr = java.text.SimpleDateFormat("h:mm a", java.util.Locale.US).format(java.util.Date(timestamp))
                            messages.add(
                                Message(
                                    id = doc.id,
                                    text = text,
                                    time = timeStr,
                                    isFromMe = senderId == (currentUser?.id ?: "guest")
                                )
                            )
                        }
                    }
                }
            }
        onDispose {
            subscription.remove()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // ── Person avatar — shows the owner's initial, NOT the room photo ──
                        Box(contentAlignment = Alignment.BottomEnd) {
                            Surface(
                                shape = CircleShape,
                                modifier = Modifier.size(40.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                    Text(
                                        text = ownerName.take(1).uppercase(),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            // Online indicator dot
                            Surface(
                                color = VerifiedGreen,
                                shape = CircleShape,
                                modifier = Modifier
                                    .size(12.dp)
                                    .border(1.5.dp, MaterialTheme.colorScheme.surface, CircleShape)
                            ) {}
                        }
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(
                                ownerName,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(VerifiedGreen)
                                )
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    "ACTIVE NOW",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground)
                    }
                },
                actions = {
                    IconButton(onClick = { }) { Icon(Icons.Rounded.Call, null, tint = MaterialTheme.colorScheme.onBackground) }
                    IconButton(onClick = { }) { Icon(Icons.Outlined.MoreVert, null, tint = MaterialTheme.colorScheme.onBackground) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            // --- Property Info Header ---
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 1.dp
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Surface(shape = RoundedCornerShape(12.dp), modifier = Modifier.size(64.dp)) {
                        AsyncImage(
                            model = propertyImage,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Inquiry about:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        Text(propertyTitle, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("ETB", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        Text(propertyPrice, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.ExtraBold)
                        Text("/month", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.weight(1f).background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text("Today", modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelSmall)
                }

                items(messages) { msg ->
                    ChatBubble(msg)
                }

                if (isOwnerTyping) {
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                "$firstName is typing",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.width(8.dp))
                            CircularProgressIndicator(
                                modifier = Modifier.size(10.dp),
                                strokeWidth = 1.5.dp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                item {
                    // Trust Banner in chat
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
                    ) {
                        Row(modifier = Modifier.padding(16.dp)) {
                            Surface(color = VerifiedGreen, shape = CircleShape, modifier = Modifier.size(24.dp)) {
                                Icon(Icons.Rounded.Shield, null, tint = Color.White, modifier = Modifier.padding(4.dp))
                            }
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text("Trust & Security", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSecondaryContainer, fontWeight = FontWeight.Bold)
                                Text(
                                    "This conversation is protected. Payments made via Telebirr through RentEasy are held in escrow until the lease is signed.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }
                }
            }

            // --- Message Input ---
            Surface(color = MaterialTheme.colorScheme.surface, shadowElevation = 8.dp) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp).navigationBarsPadding(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f), CircleShape)
                    ) {
                        Icon(Icons.Rounded.Add, null, tint = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(Modifier.width(12.dp))
                    TextField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        modifier = Modifier.weight(1f).clip(CircleShape),
                        placeholder = { Text("Type a message...", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        trailingIcon = {
                            IconButton(onClick = { }) { Icon(Icons.Rounded.SentimentSatisfiedAlt, null, tint = MaterialTheme.colorScheme.onSurfaceVariant) }
                        }
                    )
                    Spacer(Modifier.width(12.dp))
                    IconButton(
                        onClick = {
                            if (messageText.isNotBlank()) {
                                val userMessage = messageText.trim()
                                val senderId = currentUser?.id ?: "guest"
                                val msgData = hashMapOf(
                                    "text" to userMessage,
                                    "senderId" to senderId,
                                    "timestamp" to System.currentTimeMillis()
                                )
                                firestore.collection("chats").document(chatId).collection("messages").add(msgData)
                                
                                val chatMeta = hashMapOf(
                                    "chatId" to chatId,
                                    "renterId" to (if (senderId == ownerId) chatId.substringBefore("_") else senderId),
                                    "ownerId" to ownerId,
                                    "ownerName" to ownerName,
                                    "propertyTitle" to propertyTitle,
                                    "propertyPrice" to propertyPrice,
                                    "propertyImage" to propertyImage,
                                    "renterName" to (currentUser?.fullName ?: "Renter"),
                                    "lastMessage" to userMessage,
                                    "timestamp" to System.currentTimeMillis()
                                )
                                firestore.collection("chats").document(chatId).set(chatMeta, com.google.firebase.firestore.SetOptions.merge())

                                messageText = ""

                                // Trigger AI auto-reply only for demo owners
                                if (ownerId == "1" || ownerId == "2" || ownerId == "3") {
                                    scope.launch {
                                        isOwnerTyping = true
                                        delay(1800)
                                        isOwnerTyping = false

                                        val query = userMessage.lowercase()
                                        val replyText = when {
                                            query.contains("price") || query.contains("rent") || query.contains("discount") || query.contains("negotiability") || query.contains("negotiable") ->
                                                "I'm open to discussing the price. For a long-term lease, I can lower the monthly rent by up to 5,000 ETB! Let me know if you would like to arrange a call to finalize."
                                            query.contains("visit") || query.contains("view") || query.contains("see") || query.contains("tomorrow") || query.contains("schedule") ->
                                                "Absolutely, we can schedule a viewing! Does tomorrow afternoon around 2:00 PM or 4:00 PM work for you? I can show you around myself."
                                            query.contains("location") || query.contains("where") || query.contains("bole") || query.contains("address") || query.contains("district") ->
                                                "The property is located in Bole, right behind Edna Mall area. It's in a very secure, premium gated compound with 24/7 security."
                                            query.contains("hello") || query.contains("hi") || query.contains("hey") || query.contains("greetings") ->
                                                "Hello! Yes, the property is fully ready. Feel free to ask me anything about the property, security, or lease agreement!"
                                            else ->
                                                "That sounds great! Let's arrange a quick phone call to discuss the final details and set up the rent agreement."
                                        }

                                        val replyData = hashMapOf(
                                            "text" to replyText,
                                            "senderId" to ownerId,
                                            "timestamp" to System.currentTimeMillis()
                                        )
                                        firestore.collection("chats").document(chatId).collection("messages").add(replyData)

                                        val replyMeta = hashMapOf(
                                            "lastMessage" to replyText,
                                            "timestamp" to System.currentTimeMillis()
                                        )
                                        firestore.collection("chats").document(chatId).set(replyMeta, com.google.firebase.firestore.SetOptions.merge())
                                    }
                                }
                            }
                        },
                        modifier = Modifier.background(MaterialTheme.colorScheme.primary, CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Rounded.Send, null, tint = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: Message) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (message.isFromMe) Alignment.End else Alignment.Start
    ) {
        Surface(
            color = if (message.isFromMe) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (message.isFromMe) 16.dp else 4.dp,
                bottomEnd = if (message.isFromMe) 4.dp else 16.dp
            )
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp).widthIn(max = 280.dp),
                color = if (message.isFromMe) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(message.time, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            if (message.isFromMe) {
                Spacer(Modifier.width(4.dp))
                Icon(Icons.Rounded.DoneAll, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    onBack: () -> Unit,
    onNavigateToChat: (String, String, String, String, String) -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val currentUser by authViewModel.currentUser.collectAsState(initial = null)
    val firestore = remember { FirebaseFirestore.getInstance() }
    val conversations = remember { mutableStateListOf<Map<String, Any>>() }
    var isLoading by remember { mutableStateOf(true) }

    DisposableEffect(currentUser) {
        val userId = currentUser?.id ?: "guest"
        if (userId == "guest") {
            isLoading = false
            onDispose {}
        } else {
            val subscription = firestore.collection("chats")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        error.printStackTrace()
                        isLoading = false
                        return@addSnapshotListener
                    }
                    if (snapshot != null) {
                        val filtered = snapshot.documents.mapNotNull { it.data }.filter { data ->
                            val renterId = data["renterId"] as? String ?: ""
                            val ownerId = data["ownerId"] as? String ?: ""
                            renterId == userId || ownerId == userId
                        }
                        conversations.clear()
                        conversations.addAll(filtered.sortedByDescending { it["timestamp"] as? Long ?: 0L })
                    }
                    isLoading = false
                }
            
            onDispose {
                subscription.remove()
            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Messages",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onBackground)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface),
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Rounded.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.onBackground)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (currentUser == null) {
                // If in guest mode, show the lock screen
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Rounded.Lock,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "Sign In Required",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Please sign in or create an account to view your messages and chat with room owners.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            } else if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            } else if (conversations.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Rounded.Forum,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(Modifier.height(24.dp))
                    Text(
                        "No Conversations Yet",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Tap 'Message Owner' on any rental listing to start a direct, secure conversation!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(conversations) { chat ->
                        val ownerId = chat["ownerId"] as? String ?: ""
                        val renterId = chat["renterId"] as? String ?: ""
                        val ownerName = chat["ownerName"] as? String ?: "Owner"
                        val renterName = chat["renterName"] as? String ?: "Renter"
                        val propertyTitle = chat["propertyTitle"] as? String ?: "RentEasy Home"
                        val propertyPrice = chat["propertyPrice"] as? String ?: ""
                        val propertyImage = chat["propertyImage"] as? String ?: ""
                        val lastMessage = chat["lastMessage"] as? String ?: "Tap to open chat"
                        val timestamp = chat["timestamp"] as? Long ?: 0L
                        
                        val otherPersonName = if (currentUser?.id == ownerId) renterName else ownerName
                        val timeStr = if (timestamp > 0) {
                            java.text.SimpleDateFormat("h:mm a", java.util.Locale.US).format(java.util.Date(timestamp))
                        } else ""

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val targetId = if (currentUser?.id == ownerId) renterId else ownerId
                                    onNavigateToChat(
                                        targetId,
                                        otherPersonName,
                                        propertyTitle,
                                        propertyPrice,
                                        propertyImage
                                    )
                                },
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(16.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // ── Person avatar — first initial of contact name ──
                                Surface(
                                    shape = CircleShape,
                                    modifier = Modifier.size(52.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Text(
                                            text = otherPersonName.take(1).uppercase(),
                                            style = MaterialTheme.typography.titleLarge,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Spacer(Modifier.width(16.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = otherPersonName,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                    // ── Property label — clearly marked as the property ──
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(top = 2.dp)
                                    ) {
                                        Icon(
                                            Icons.Rounded.Home,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(11.dp)
                                        )
                                        Spacer(Modifier.width(3.dp))
                                        Text(
                                            text = propertyTitle,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.SemiBold,
                                            maxLines = 1,
                                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                        )
                                    }
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = lastMessage,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 1,
                                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                    )
                                }
                                // ── Right side: property thumbnail + timestamp ──
                                Column(
                                    horizontalAlignment = Alignment.End,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = timeStr,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(Modifier.height(6.dp))
                                    if (propertyImage.isNotEmpty()) {
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            modifier = Modifier.size(width = 44.dp, height = 36.dp)
                                        ) {
                                            AsyncImage(
                                                model = propertyImage,
                                                contentDescription = "Property",
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                    } else {
                                        Icon(
                                            Icons.Rounded.ChevronRight,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                            modifier = Modifier.size(20.dp)
                                        )
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
