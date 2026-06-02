package com.renteasy.app.ui.screens.owner

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.renteasy.app.domain.model.PropertyCategory
import com.renteasy.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPropertyScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddPropertyViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = androidx.compose.ui.platform.LocalContext.current
    
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { selectedUri ->
            try {
                val inputStream = context.contentResolver.openInputStream(selectedUri)
                val tempFile = java.io.File(context.cacheDir, "temp_upload_${System.currentTimeMillis()}.jpg")
                val outputStream = java.io.FileOutputStream(tempFile)
                inputStream?.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
                viewModel.addImage(tempFile.toURI().toString())
            } catch (e: Exception) {
                android.util.Log.e("AddPropertyScreen", "Failed to cache selected image: ${e.message}", e)
            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("List New Property", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, null, tint = MaterialTheme.colorScheme.onBackground)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // --- Section 1: Basic Info ---
            item {
                SectionHeader("Basic Information", Icons.Rounded.Description)
                Spacer(Modifier.height(16.dp))
                RentInput(
                    value = uiState.title,
                    onValueChange = viewModel::updateTitle,
                    label = "Property Title",
                    placeholder = "e.g. Luxury Apartment in Bole"
                )
                Spacer(Modifier.height(12.dp))
                RentInput(
                    value = uiState.description,
                    onValueChange = viewModel::updateDescription,
                    label = "Description",
                    placeholder = "Detailed description of your property...",
                    singleLine = false,
                    modifier = Modifier.height(120.dp)
                )
            }

            // --- Section 2: Category & Price ---
            item {
                SectionHeader("Details & Pricing", Icons.Rounded.Category)
                Spacer(Modifier.height(16.dp))
                
                Text("Category", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp, modifier = Modifier.padding(bottom = 8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(PropertyCategory.values()) { category ->
                        CategoryChip(
                            label = category.name,
                            isSelected = uiState.category == category,
                            onClick = { viewModel.updateCategory(category) }
                        )
                    }
                }
                
                Spacer(Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    RentInput(
                        value = uiState.price,
                        onValueChange = viewModel::updatePrice,
                        label = "Price (ETB/mo)",
                        placeholder = "5000",
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.weight(1f)
                    )
                    RentInput(
                        value = uiState.areaM2,
                        onValueChange = viewModel::updateArea,
                        label = "Area (m²)",
                        placeholder = "120",
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // --- Section 3: Location ---
            item {
                SectionHeader("Location", Icons.Rounded.LocationOn)
                Spacer(Modifier.height(16.dp))
                RentInput(
                    value = uiState.location,
                    onValueChange = viewModel::updateLocation,
                    label = "Full Address",
                    placeholder = "Street address, Building name"
                )
                Spacer(Modifier.height(12.dp))
                RentInput(
                    value = uiState.district,
                    onValueChange = viewModel::updateDistrict,
                    label = "District (Sub-city)",
                    placeholder = "e.g. Bole, Kirkos, Yeka"
                )
            }

            // --- Section 4: Features ---
            item {
                SectionHeader("Key Features", Icons.Rounded.KingBed)
                Spacer(Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    CounterInput(
                        label = "Bedrooms",
                        value = uiState.bedrooms,
                        onValueChange = viewModel::updateBedrooms,
                        modifier = Modifier.weight(1f)
                    )
                    CounterInput(
                        label = "Bathrooms",
                        value = uiState.bathrooms,
                        onValueChange = viewModel::updateBathrooms,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // --- Section 5: Images (Demo) ---
            item {
                SectionHeader("Property Images (${uiState.images.size})", Icons.Rounded.Image)
                Spacer(Modifier.height(16.dp))
                
                if (uiState.images.isNotEmpty()) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(bottom = 12.dp)
                    ) {
                        items(uiState.images) { imageUrl ->
                            Surface(
                                modifier = Modifier.size(100.dp),
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surface,
                                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                            ) {
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clickable { photoPickerLauncher.launch("image/*") },
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Rounded.AddAPhoto, null, tint = MaterialTheme.colorScheme.primary)
                        Text("Add Photos", color = MaterialTheme.colorScheme.onBackground, fontSize = 14.sp)
                        Text("Supports JPG, PNG", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp)
                    }
                }
            }

            // --- Submit Button ---
            item {
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.submitProperty(onNavigateBack) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(16.dp),
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Publish Listing", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
                
                if (uiState.error != null) {
                    Text(uiState.error!!, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
                }
                
                Spacer(Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, icon: ImageVector) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(8.dp))
        Text(title, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true
) {
    Column(modifier = modifier) {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp, modifier = Modifier.padding(bottom = 6.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = singleLine
        )
    }
}

@Composable
fun CounterInput(label: String, value: Int, onValueChange: (Int) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp, modifier = Modifier.padding(bottom = 6.dp))
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { if (value > 1) onValueChange(value - 1) }) {
                    Icon(Icons.Rounded.Remove, null, tint = MaterialTheme.colorScheme.onBackground)
                }
                Text(value.toString(), color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
                IconButton(onClick = { onValueChange(value + 1) }) {
                    Icon(Icons.Rounded.Add, null, tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
fun CategoryChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.clickable { onClick() },
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline)
    ) {
        Text(
            label,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 12.sp
        )
    }
}
