package com.point.auth.registration.presenter.credentials

sealed interface CredentialsAction {

    sealed interface Action : CredentialsAction {

        @JvmInline
        value class OnLoginInput(val value: String) : Action

        @JvmInline
        value class OnEmailInput(val value: String) : Action

        @JvmInline
        value class OnCodeInput(val value: String) : Action

        @JvmInline
        value class OnPasswordInput(val value: String) : Action

        @JvmInline
        value class OnPasswordSecondInput(val value: String) : Action

        data object OnRegistration : Action

    }
}