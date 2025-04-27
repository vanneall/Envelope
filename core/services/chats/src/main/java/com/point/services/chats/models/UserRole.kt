package com.point.services.chats.models

import com.point.services.chats.responses.UserRoleResponse

data class UserRole(
    val name: String,
    val canSentMessages: Boolean = false,
    val canInviteUsers: Boolean = false,
    val canPinMessages: Boolean = false,
    val canSetRoles: Boolean = false,
    val canDeleteUsers: Boolean = false,
)

internal fun UserRoleResponse.toModel() = UserRole(
    name = name,
    canSentMessages = canSentMessages,
    canInviteUsers = canInviteUsers,
    canPinMessages = canPinMessages,
    canSetRoles = canSetRoles,
    canDeleteUsers = canDeleteUsers,
)