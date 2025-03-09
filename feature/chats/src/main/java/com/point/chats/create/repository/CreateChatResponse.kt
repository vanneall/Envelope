package com.point.chats.create.repository

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateChatResponse(
    @SerialName("id")
    val id: String,
)