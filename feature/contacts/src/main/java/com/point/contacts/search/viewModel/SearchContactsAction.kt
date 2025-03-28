package com.point.contacts.search.viewModel

import com.point.contacts.data.response.UserInfoShort

sealed interface SearchContactsAction {

    data class OnNameTyped(val query: String) : SearchContactsAction

    data class SendRequest(val userId: String) : SearchContactsAction

    data class LoadUserContacts(val contacts: List<UserInfoShort>) : SearchContactsAction

}