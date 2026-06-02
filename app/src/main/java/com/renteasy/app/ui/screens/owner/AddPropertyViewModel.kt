package com.renteasy.app.ui.screens.owner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renteasy.app.domain.model.*
import com.renteasy.app.domain.repository.PropertyRepository
import com.renteasy.app.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddPropertyViewModel @Inject constructor(
    private val propertyRepository: PropertyRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddPropertyUiState>(AddPropertyUiState())
    val uiState: StateFlow<AddPropertyUiState> = _uiState.asStateFlow()

    fun updateTitle(title: String) { _uiState.value = _uiState.value.copy(title = title) }
    fun updateDescription(description: String) { _uiState.value = _uiState.value.copy(description = description) }
    fun updateLocation(location: String) { _uiState.value = _uiState.value.copy(location = location) }
    fun updateDistrict(district: String) { _uiState.value = _uiState.value.copy(district = district) }
    fun updatePrice(price: String) { _uiState.value = _uiState.value.copy(price = price) }
    fun updateCategory(category: PropertyCategory) { _uiState.value = _uiState.value.copy(category = category) }
    fun updateBedrooms(count: Int) { _uiState.value = _uiState.value.copy(bedrooms = count) }
    fun updateBathrooms(count: Int) { _uiState.value = _uiState.value.copy(bathrooms = count) }
    fun updateArea(area: String) { _uiState.value = _uiState.value.copy(areaM2 = area) }
    fun addImage(url: String) { _uiState.value = _uiState.value.copy(images = _uiState.value.images + url) }

    fun submitProperty(onSuccess: () -> Unit) {
        val user = authRepository.currentUser.value ?: return
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            val property = Property(
                id = UUID.randomUUID().toString(),
                title = _uiState.value.title,
                location = _uiState.value.location,
                district = _uiState.value.district,
                pricePerMonth = _uiState.value.price.toIntOrNull() ?: 0,
                isVerified = false,
                status = PropertyStatus.PENDING,
                images = _uiState.value.images.ifEmpty { listOf("https://images.unsplash.com/photo-1560518883-ce09059eeffa?auto=format&fit=crop&q=80&w=800") },
                category = _uiState.value.category,
                bedrooms = _uiState.value.bedrooms,
                bathrooms = _uiState.value.bathrooms,
                areaM2 = _uiState.value.areaM2.toIntOrNull() ?: 0,
                description = _uiState.value.description,
                ownerId = user.id,
                ownerName = user.fullName,
                ownerAvatarUrl = user.avatarUrl, // persist owner's profile photo with the listing
                ownerRating = user.rating,
                createdAt = System.currentTimeMillis()
            )

            val result = propertyRepository.addProperty(property)
            if (result.isSuccess) {
                onSuccess()
            } else {
                _uiState.value = _uiState.value.copy(error = result.exceptionOrNull()?.message, isLoading = false)
            }
        }
    }
}

data class AddPropertyUiState(
    val title: String = "",
    val description: String = "",
    val location: String = "",
    val district: String = "",
    val price: String = "",
    val category: PropertyCategory = PropertyCategory.HOUSE,
    val bedrooms: Int = 1,
    val bathrooms: Int = 1,
    val areaM2: String = "",
    val images: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
