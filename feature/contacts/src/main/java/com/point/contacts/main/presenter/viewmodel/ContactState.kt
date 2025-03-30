package com.point.contacts.main.presenter.viewmodel

import androidx.compose.ui.graphics.Color

data class ContactState(
    val contacts: List<Contact> = emptyList(),
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
    val image: Color = Color.Gray,
    val inContacts: Boolean = false,
    val isSentRequest: Boolean = false,
)