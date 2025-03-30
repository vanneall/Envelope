package com.example.settings.profile.viewmodel

import com.example.settings.data.UserProfileDetailedResponse

sealed interface ProfileEditAction {

    data class OnDataFetchedSuccess(val data: UserProfileDetailedResponse): ProfileEditAction

    data class OnNameEntered(val value: String) : ProfileEditAction
    data class OnStatusEntered(val value: String) : ProfileEditAction
    data class OnAboutEntered(val value: String) : ProfileEditAction

    data object OnSavePressed : ProfileEditAction
    data object OnSaveSuccessfully : ProfileEditAction
}