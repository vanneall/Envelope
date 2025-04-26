package com.point.chats.multi.info.viewmodel

import com.point.chats.main.data.entity.response.ChatType
import com.point.chats.multi.info.data.UserRole

data class MultiChatInfoState(
    val id: String = "",
    val name: String = "",
    val description: String? = null,
    val type: ChatType = ChatType.PRIVATE,
    val chatPreviewPhotos: List<String> = emptyList(),
    val mediaContentIds: List<String> = emptyList(),
    val chatUsers: List<UserInfo> = emptyList(),
)

data class UserInfo(
    val id: String,
    val name: String,
    val photoId: String?,
    val userRole: UserRole,
)