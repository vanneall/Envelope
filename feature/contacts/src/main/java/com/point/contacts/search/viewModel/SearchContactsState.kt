package com.point.contacts.search.viewModel

import com.point.contacts.main.presenter.viewmodel.Contact

internal data class SearchContactsState(
    val isRefreshing: Boolean = false,
    val name: String = "",
    val inContacts: List<Contact> = emptyList(),
    val globalContacts: List<Contact> = emptyList(),
)