package com.point.chats.main.data.entity.response

import com.point.network.di.InstantSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class ChatInfoShort(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("photo")
    val photoId: Long?,
    @SerialName("type")
    val type: ChatType,
    @SerialName("last_message")
    val lastMessage: MessageInfoShort?,
)

@Serializable
data class MessageInfoShort(
    @SerialName("id")
    val id: String,
    @SerialName("text")
    val text: String,
    @Serializable(with = InstantSerializer::class)
    @SerialName("timestamp")
    val timestamp: Instant,
)

enum class ChatType {
    PRIVATE,
    ONE_ON_ONE,
    MANY,
    ;
}