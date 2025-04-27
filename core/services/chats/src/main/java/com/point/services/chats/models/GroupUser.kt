package com.point.services.chats.models

import com.point.services.chats.responses.GroupUserResponse

data class GroupUser(
    val id: String,
    val name: String,
    val photoId: Long?,
    val role: UserRole,
)

internal fun GroupUserResponse.toModel() = GroupUser(
    id = id,
    name = name,
    photoId = photoId,
    role = userRoleResponse.toModel(),
)