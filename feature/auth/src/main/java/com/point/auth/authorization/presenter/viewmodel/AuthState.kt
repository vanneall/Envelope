package com.point.auth.authorization.presenter.viewmodel

data class AuthState(
    val login: String = "",
    val password: String = "",
    val isInvalidCredentials: Boolean = false,
)