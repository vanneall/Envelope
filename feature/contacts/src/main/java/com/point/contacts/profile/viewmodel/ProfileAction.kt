package com.point.contacts.profile.viewmodel

import com.point.contacts.data.UserInfoShortResponse

sealed interface ProfileAction {

    data object Refresh : ProfileAction

    data class ProfileDataLoaded(val data: UserInfoShortResponse) : ProfileAction

}