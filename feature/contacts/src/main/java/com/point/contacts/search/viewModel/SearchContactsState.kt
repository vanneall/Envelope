package com.point.contacts.search.viewModel

import com.point.contacts.main.presenter.viewmodel.Contact

data class SearchContactsState(
    val query: String = "",
    val inContacts: List<Contact> = emptyList(),
    val allContacts: List<Contact> = emptyList(),
)