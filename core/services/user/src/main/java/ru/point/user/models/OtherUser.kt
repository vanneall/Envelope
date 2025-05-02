package ru.point.user.models

import ru.point.user.responses.OtherUserResponse

data class OtherUser(
    val username: String,
    val name: String,
    val status: String?,
    val photo: String?,
    val inContacts: Boolean,
    val inSentRequests: Boolean,
)

internal fun OtherUserResponse.toModel() = OtherUser(
    username = username,
    name = name,
    status = status,
    photo = lastPhoto?.let { photoId -> "http://192.168.0.174:8084/media/$photoId" },
    inContacts = inContacts,
    inSentRequests = inSentRequests,
)
