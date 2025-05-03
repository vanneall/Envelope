package com.example.settings.main.viewmodel

import ru.point.user.models.DetailedUserProfile

sealed interface SettingsAction {

    sealed interface Action : SettingsAction {

        data object Refresh : Action

        data object LeftFromAccount : Action
    }

    sealed interface Event : SettingsAction {

        data class UserDataFetched(
            val data: DetailedUserProfile,
        ) : Event

    }
}