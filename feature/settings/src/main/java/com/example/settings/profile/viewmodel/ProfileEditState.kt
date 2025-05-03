package com.example.settings.profile.viewmodel

import android.net.Uri
import java.time.LocalDate

data class ProfileEditState(
    val name: String = "",
    val status: String = "",
    val about: String = "",
    val initialPhotoUrl: String? = null,
    val photoUri: Uri? = null,
    val date: LocalDate = LocalDate.now(),
)