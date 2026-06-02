package com.renteasy.app.ui.screens.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renteasy.app.data.FavoritesManager
import com.renteasy.app.domain.model.Property
import com.renteasy.app.domain.repository.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SavedHomesViewModel @Inject constructor(
    private val propertyRepository: PropertyRepository,
    private val favoritesManager: FavoritesManager
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    val savedProperties: StateFlow<List<Property>> = propertyRepository.getProperties()
        .combine(favoritesManager.favoriteIds) { properties, favoriteIds ->
            _isLoading.value = false
            properties.filter { favoriteIds.contains(it.id) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
