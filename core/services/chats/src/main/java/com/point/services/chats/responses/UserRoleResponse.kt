package com.point.services.chats.responses

import kotlinx.serialization.Serializable

@Serializable
internal class UserRoleResponse(
    val name: String,
    val canSentMessages: Boolean = false,
    val canInviteUsers: Boolean = false,
    val canPinMessages: Boolean = false,
    val canSetRoles: Boolean = false,
    val canDeleteUsers: Boolean = false,
)