package com.point.services.chats.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class GroupUserResponse(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("photo_id")
    val photoId: Long?,
    @SerialName("user_role")
    val userRoleResponse: UserRoleResponse,
)