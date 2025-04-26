package com.point.contacts.profile.viewmodel

import ru.point.user.models.UserInfo

sealed interface ProfileAction {

    data object Refresh : ProfileAction

    data class ProfileDataLoaded(val data: UserInfo) : ProfileAction

    data object ToChat : ProfileAction

    data object DeleteFromContacts : ProfileAction
    data object UserDeletedSuccessfully : ProfileAction

    data object AddContact : ProfileAction
    data object SentRequestSuccessfully : ProfileAction

}