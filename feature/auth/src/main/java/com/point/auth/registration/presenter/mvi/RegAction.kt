package com.point.auth.registration.presenter.mvi

sealed interface RegAction {

    sealed interface Action : RegAction {
        data class OnLoginInput(val value: String) : Action
        data class OnPasswordInput(val value: String) : Action
        data class OnNameInput(val value: String) : Action

        data object OnLoginClear: Action
        data object OnPasswordClear: Action
        data object OnNameClear: Action

        data object OnRegistration : Action
    }
}