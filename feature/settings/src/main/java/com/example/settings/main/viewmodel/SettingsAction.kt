package com.example.settings.main.viewmodel

sealed interface SettingsAction {
    
    data object Refresh : SettingsAction
    
}