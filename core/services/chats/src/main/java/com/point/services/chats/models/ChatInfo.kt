package com.point.services.chats.models

import com.point.services.chats.responses.ChatInfoResponse

data class ChatInfo(
    val id: String,
    val name: String,
    val type: ChatType,
    val photo: String? = null,
    val lastMessage: LastMessage? = null,
)

internal fun ChatInfoResponse.toModel() = ChatInfo(
    id = id,
    name = name,
    type = type,
    photo = photo?.let { uri -> "http://192.168.0.174:8084/media/$uri" },
    lastMessage = lastMessage?.toModel(),
)