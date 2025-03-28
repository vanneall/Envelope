package com.point.auth.registration.presenter.credentials

data class CredentialsState(
    val login: String = "",
    val password: String = "",
    val repassword: String = "",
)