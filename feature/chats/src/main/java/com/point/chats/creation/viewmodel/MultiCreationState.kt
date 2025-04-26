package com.point.chats.creation.viewmodel

import com.point.chats.creation.data.User

data class MultiCreationState(val users: List<User> = emptyList()) {
    val isAvailableToCreate
        get() = users.count { it.checked } > 1
}