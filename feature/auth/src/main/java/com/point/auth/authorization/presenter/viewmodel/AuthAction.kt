package com.point.auth.authorization.presenter.viewmodel

sealed interface AuthAction {

    sealed interface Action : AuthAction {

        @JvmInline
        value class OnLoginInput(val value: String) : Action
        @JvmInline
        value class OnPasswordInput(val value: String) : Action

        data object Authorization : Action
    }

    sealed interface Event : AuthAction {

        data object OnAuthorizationSuccess : Event
        data object OnAuthorizationFailed : Event

        data object OnFieldsEmpty : Event
    }
}