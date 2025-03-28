package com.point.contacts.requests.viewModel

import com.point.contacts.main.presenter.viewmodel.Contact

data class RequestsState(
    val query: String = "",
    val contacts: List<Contact> = emptyList(),
    val isRefreshing: Boolean = false,
)