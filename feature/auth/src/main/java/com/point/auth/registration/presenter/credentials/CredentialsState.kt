package com.point.auth.registration.presenter.credentials

data class CredentialsState(
    val login: String = "",
    val password: String = "",
    val email: String = "",
    val code: String = "",
    val passwordSecondInput: String = "",
)