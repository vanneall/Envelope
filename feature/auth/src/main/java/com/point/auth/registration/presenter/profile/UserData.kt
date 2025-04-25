package com.point.auth.registration.presenter.profile

import android.net.Uri

data class UserData(
    val name: String,
    val status: String?,
    val about: String?,
    val uri: Uri?,
)
