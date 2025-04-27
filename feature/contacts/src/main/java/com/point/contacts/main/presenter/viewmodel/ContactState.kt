package com.point.contacts.main.presenter.viewmodel

data class ContactState(
    val contacts: Map<Char, List<Contact>> = emptyMap(),
    val isRefreshing: Boolean = false,
    val isRefreshingEnabled: Boolean = false,
    val isInitialLoading: Boolean = true,
    val search: String = "",
    val isError: Boolean = false,
)

data class Contact(
    val username: String = "",
    val name: String = "",
    val status: String = "",
    val photo: String? = null,
    val inContacts: Boolean = false,
    val isSentRequest: Boolean = false,
)