package com.example.settings.profile.viewmodel

import android.net.Uri
import com.example.settings.data.UserProfileDetailedResponse

sealed interface ProfileEditAction {

    @JvmInline
    value class OnDataFetchedSuccess(val data: UserProfileDetailedResponse): ProfileEditAction

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
}