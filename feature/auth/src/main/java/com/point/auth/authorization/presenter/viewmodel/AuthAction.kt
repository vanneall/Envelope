package com.point.auth.authorization.presenter.viewmodel

import androidx.annotation.StringRes

sealed interface AuthAction {

    sealed interface Action : AuthAction {

        @JvmInline
        value class OnLoginInput(val value: String) : Action
        @JvmInline
        value class OnPasswordInput(val value: String) : Action

        data object Authorization : Action
    }

    sealed interface SideEffect : AuthAction {

        data object OnAuthorizationSuccess : SideEffect
        data object OnAuthorizationFailed : SideEffect

        @JvmInline
        value class OnLoginFieldInvalid(@StringRes val reason: Int) : SideEffect

        @JvmInline
        value class OnPasswordFieldInvalid(@StringRes val reason: Int) : SideEffect

        @JvmInline
        value class BothFieldInvalid(@StringRes val reason: Int) : SideEffect
    }
}