package com.point.chats.creation.single.mvi.state

import androidx.compose.runtime.Immutable
import ru.point.user.models.UserContact

@Immutable
internal data class ChatCreationState(
    val users: Map<String, List<UserUi>> = emptyMap(),
)

internal fun UserContact.toUi() = UserUi(
    username = username,
    name = name,
    photo = lastPhoto,
    status = status,
)

@Immutable
internal data class UserUi(val username: String, val name: String, val photo: String?, val status: String?)