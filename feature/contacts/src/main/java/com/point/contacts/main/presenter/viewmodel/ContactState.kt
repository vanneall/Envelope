package com.point.contacts.main.presenter.viewmodel

import androidx.compose.ui.graphics.Color

data class ContactState(
    val contacts: List<Contact> = emptyList(),
    val isRefreshing: Boolean = false,
    val search: String = "",
    val isError: Boolean = false,
)

data class Contact(
    val id: String = "",
    val name: String = "",
    val status: String = "",
    val image: Color = Color.Gray,
)