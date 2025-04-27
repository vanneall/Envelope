package com.point.services.chats.responses

import com.point.services.chats.models.ChatType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class GroupChatInfoResponse(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String?,
    @SerialName("type")
    val type: ChatType,
    @SerialName("chat_preview_photo_ids")
    val chatPreviewPhotosIds: List<Long>,
    @SerialName("chat_users")
    val users: List<GroupUserResponse>,
    @SerialName("media_content_ids")
    val mediaContentIds: List<Long>
)
