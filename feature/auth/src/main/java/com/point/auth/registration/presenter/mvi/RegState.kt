package com.point.auth.registration.presenter.mvi

data class RegState(
    val login: String = "",
    val password: String = "",
    val name: String = "",
    val isInvalidCredentials: Boolean = false,
)