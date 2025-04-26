package ru.point.user.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class SearchUsersResponse(
    @SerialName("in_contacts")
    val inContacts: List<OtherUserResponse>,
    @SerialName("all_contacts")
    val allContacts: List<OtherUserResponse>,
)
