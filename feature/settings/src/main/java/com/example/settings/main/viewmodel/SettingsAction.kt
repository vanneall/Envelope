package com.example.settings.main.viewmodel

import com.example.settings.data.UserProfileDetailedResponse

sealed interface SettingsAction {

    sealed interface Action : SettingsAction {

        data object Refresh : Action

        data object LeftFromAccount : Action
    }

    sealed interface Event : SettingsAction {

        data class UserDataFetched(
            val data: UserProfileDetailedResponse,
        ) : Event

    }
}