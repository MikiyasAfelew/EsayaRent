package com.renteasy.app.ui.screens.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renteasy.app.domain.model.Property
import com.renteasy.app.domain.repository.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val propertyRepository: PropertyRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _selectedProperty = MutableStateFlow<Property?>(null)
    val selectedProperty: StateFlow<Property?> = _selectedProperty.asStateFlow()

    val properties: StateFlow<List<Property>> = propertyRepository.getProperties()
        .onEach { list ->
            _isLoading.value = false
            if (list.isEmpty()) {
                _selectedProperty.value = null
            } else if (_selectedProperty.value == null || !list.contains(_selectedProperty.value)) {
                _selectedProperty.value = list.first()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun selectProperty(property: Property) {
        _selectedProperty.value = property
    }
}
