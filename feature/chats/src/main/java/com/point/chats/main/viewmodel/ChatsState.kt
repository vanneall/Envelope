package com.point.chats.main.viewmodel

import com.point.chats.main.viewmodel.items.Mode
import com.point.ui.items.UiItem

data class ChatsState(
    val isInitialLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val isError: Boolean = false,
    val chats: List<UiItem> = emptyList(),
    val mode: Mode = Mode.IDLE,
)
