package com.point.services.chats.models

import com.point.services.chats.responses.GroupChatInfoResponse

data class GroupChatInfo(
    val id: String,
    val name: String,
    val description: String?,
    val type: ChatType,
    val chatPreviewPhotosIds: List<String>,
    val users: List<GroupUser>,
    val mediaContentIds: List<Long>,
)

internal fun GroupChatInfoResponse.toModel() = GroupChatInfo(
    id = id,
    name = name,
    description = description,
    type = type,
    chatPreviewPhotosIds = chatPreviewPhotosIds.map { uri -> "http://192.168.0.174:8084/photos/$uri" },
    users = users.map { response -> response.toModel() },
    mediaContentIds = mediaContentIds,
)