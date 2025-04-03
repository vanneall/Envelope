package com.example.settings.main.viewmodel

sealed interface SettingsAction {

    sealed interface Action : SettingsAction {

        data object Refresh : Action

        data object LeftFromAccount : Action
    }
}