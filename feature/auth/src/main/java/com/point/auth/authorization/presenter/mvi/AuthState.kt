package com.point.auth.authorization.presenter.mvi

data class AuthState(
    val login: String = "",
    val password: String = "",
    val isInvalidCredentials: Boolean = false,
)