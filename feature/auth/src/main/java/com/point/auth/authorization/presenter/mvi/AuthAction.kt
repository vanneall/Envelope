package com.point.auth.authorization.presenter.mvi

sealed interface AuthAction {

    sealed interface Action : AuthAction {
        data class OnLoginInput(val value: String) : Action
        data class OnPasswordInput(val value: String) : Action

        data object OnLoginClear: Action
        data object OnPasswordClear: Action

        data object Authorization : Action
    }

    sealed interface Event : AuthAction {
        data object OnAuthorizationSuccess : Event
        data object OnAuthorizationFailed : Event

        data object OnFieldsEmpty : Event
    }
}