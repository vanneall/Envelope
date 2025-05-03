package com.example.settings.profile.viewmodel

import android.net.Uri
import ru.point.user.models.UserInfo
import java.time.LocalDate

sealed interface ProfileEditAction {

    @JvmInline
    value class OnDataFetchedSuccess(val data: UserInfo) : ProfileEditAction

    @JvmInline
    value class OnNameEntered(val value: String) : ProfileEditAction
    @JvmInline
    value class OnStatusEntered(val value: String) : ProfileEditAction
    @JvmInline
    value class OnAboutEntered(val value: String) : ProfileEditAction

    data object OnSavePressed : ProfileEditAction
    data object OnSaveSuccessfully : ProfileEditAction

    @JvmInline
    value class PickPhoto(val uri: Uri) : ProfileEditAction

    @JvmInline
    value class PickDate(val localDate: LocalDate) : ProfileEditAction
}