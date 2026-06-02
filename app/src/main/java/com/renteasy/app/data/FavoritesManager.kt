package com.renteasy.app.data

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences("renteasy_favorites", Context.MODE_PRIVATE)
    
    private val _favoriteIds = MutableStateFlow<Set<String>>(getFavoritesFromPrefs())
    val favoriteIds: StateFlow<Set<String>> = _favoriteIds.asStateFlow()

    private fun getFavoritesFromPrefs(): Set<String> {
        // String set returned by getStringSet must not be modified directly.
        // Copying it to a new Set is required.
        val set = prefs.getStringSet("favorite_ids", emptySet()) ?: emptySet()
        return set.toSet()
    }

    private fun saveFavoritesToPrefs(favorites: Set<String>) {
        prefs.edit().putStringSet("favorite_ids", favorites).apply()
    }

    fun isFavorite(propertyId: String): Boolean {
        return _favoriteIds.value.contains(propertyId)
    }

    fun toggleFavorite(propertyId: String) {
        val current = _favoriteIds.value.toMutableSet()
        if (current.contains(propertyId)) {
            current.remove(propertyId)
        } else {
            current.add(propertyId)
        }
        _favoriteIds.value = current
        saveFavoritesToPrefs(current)
    }
}
