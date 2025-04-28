package com.point.contacts.requests.viewModel

import com.point.ui.components.user.UserCardButtonInfo

internal data class RequestsState(
    val query: String = "",
    val contacts: List<RequestUi> = emptyList(),
    val isRefreshing: Boolean = false,
    val isRefreshingEnabled: Boolean = false,
    val isInitialLoading: Boolean = true,
)

internal data class RequestUi(val id: Long, val username: String, val userBase: UserCardButtonInfo)