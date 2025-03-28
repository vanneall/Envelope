package com.point.contacts.main.presenter.viewmodel

import com.point.contacts.data.response.UserInfoShort

sealed interface ContactsActions {

    data class LoadUserContacts(val contacts: List<UserInfoShort>) : ContactsActions

    data object Refresh : ContactsActions

}