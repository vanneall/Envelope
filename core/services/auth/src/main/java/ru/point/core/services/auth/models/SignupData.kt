package ru.point.core.services.auth.models

import android.net.Uri

data class SignupData(
    val login: String,
    val password: String,
    val name: String,
    val status: String?,
    val about: String?,
    val uri: Uri?,
    val email: String,
    val code: String,
)