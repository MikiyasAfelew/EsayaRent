package com.renteasy.app.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renteasy.app.domain.model.Property
import com.renteasy.app.domain.repository.PropertyRepository
import com.renteasy.app.data.FavoritesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@HiltViewModel
class PropertyDetailViewModel @Inject constructor(
    private val repository: PropertyRepository,
    private val favoritesManager: FavoritesManager
) : ViewModel() {

    private val _propertyId = MutableStateFlow<String?>(null)

    val property: StateFlow<Property?> = _propertyId
        .filterNotNull()
        .flatMapLatest { id -> repository.getPropertyById(id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val isFavourite: StateFlow<Boolean> = _propertyId
        .filterNotNull()
        .flatMapLatest { id ->
            favoritesManager.favoriteIds.map { it.contains(id) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun setPropertyId(id: String) {
        _propertyId.value = id
    }

    fun toggleFavourite() {
        val id = _propertyId.value ?: return
        favoritesManager.toggleFavorite(id)
    }
}
