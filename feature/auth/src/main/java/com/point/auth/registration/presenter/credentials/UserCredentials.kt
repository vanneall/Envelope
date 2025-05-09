package com.point.auth.registration.presenter.credentials

data class UserCredentials(
    val username: String,
    val password: String,
    val email: String,
    val code: String,
)