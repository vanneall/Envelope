package com.point.chats.multi.info.data

import com.point.chats.main.data.entity.response.ChatType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GroupChatInfo(
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
    val users: List<GroupUser>,
    @SerialName("media_content_ids")
    val mediaContentIds: List<Long>
)

@Serializable
data class GroupUser(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("photo_id")
    val photoId: Long?,
    @SerialName("user_role")
    val userRole: UserRole,
)

@Serializable
data class UserRole(
    val name: String,
    val canSentMessages: Boolean = false,
    val canInviteUsers: Boolean = false,
    val canPinMessages: Boolean = false,
    val canSetRoles: Boolean = false,
    val canDeleteUsers: Boolean = false,
)