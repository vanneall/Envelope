package ru.point.user.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class UsersSearchResponse(
    @SerialName("in_contacts")
    val inContacts: List<OtherUserResponse>,
    @SerialName("all_contacts")
    val allContacts: List<OtherUserResponse>,
)
