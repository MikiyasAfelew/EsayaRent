package com.renteasy.app.ui.theme

import kotlinx.coroutines.flow.MutableStateFlow

object ThemeController {
    private var isInitialized = false
    val isDarkMode = MutableStateFlow(true)
    
    fun initialize(systemDark: Boolean) {
        if (!isInitialized) {
            isDarkMode.value = systemDark
            isInitialized = true
        }
    }
    
    fun toggle() {
        isDarkMode.value = !isDarkMode.value
        isInitialized = true
    }
}
