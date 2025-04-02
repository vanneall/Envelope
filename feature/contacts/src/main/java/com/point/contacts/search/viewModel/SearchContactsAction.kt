package com.point.contacts.search.viewModel

import com.point.contacts.data.UsersSearchResponse

sealed interface SearchContactsAction {

    data class OnNameTyped(val query: String) : SearchContactsAction

    data class SendRequest(val userId: String) : SearchContactsAction

    data class LoadUserContacts(val contacts: UsersSearchResponse) : SearchContactsAction

}