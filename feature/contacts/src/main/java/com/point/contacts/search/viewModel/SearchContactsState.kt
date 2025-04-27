package com.point.contacts.search.viewModel

import com.point.contacts.search.data.ContactUserUi

internal data class SearchContactsState(
    val isRefreshing: Boolean = false,
    val name: String = "",
    val inContacts: List<ContactUserUi> = emptyList(),
    val globalContacts: List<ContactUserUi> = emptyList(),
)