package com.point.auth.registration.presenter.host

import android.net.Uri

data class UserRegistrationData(
    val login: String,
    val password: String,
    val name: String,
    val status: String?,
    val about: String?,
    val uri: Uri?,
)
