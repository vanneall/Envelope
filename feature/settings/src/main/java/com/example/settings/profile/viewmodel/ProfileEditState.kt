package com.example.settings.profile.viewmodel

import android.net.Uri

data class ProfileEditState(
    val name: String = "",
    val status: String = "",
    val about: String = "",
    val initialPhotoUrl: String? = null,
    val photoUri: Uri? = null,
)