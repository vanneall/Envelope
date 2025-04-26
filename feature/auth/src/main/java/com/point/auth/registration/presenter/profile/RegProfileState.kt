package com.point.auth.registration.presenter.profile

import android.net.Uri

data class RegProfileState(
    val name: String = "",
    val status: String = "",
    val about: String = "",
    val uri: Uri? = null,
)