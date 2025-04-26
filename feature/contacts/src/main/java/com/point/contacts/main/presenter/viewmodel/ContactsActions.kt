package com.point.contacts.main.presenter.viewmodel

import ru.point.user.responses.UserInfoShort

sealed interface ContactsActions {

    data class LoadUserContacts(val contacts: List<UserInfoShort>) : ContactsActions

    data object Refresh : ContactsActions

}