package com.point.contacts.main.presenter.viewmodel

import ru.point.user.models.UserContact

sealed interface ContactsActions {

    data class LoadUserContacts(val contacts: List<UserContact>) : ContactsActions

    data object Refresh : ContactsActions

}