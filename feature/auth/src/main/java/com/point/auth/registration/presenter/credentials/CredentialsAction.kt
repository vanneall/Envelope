package com.point.auth.registration.presenter.credentials

sealed interface CredentialsAction {

    data class OnLoginInput(val value: String) : CredentialsAction

    data class OnPasswordInput(val value: String) : CredentialsAction

    data class OnRepasswordInput(val value: String) : CredentialsAction

    data object OnRegistration : CredentialsAction

}