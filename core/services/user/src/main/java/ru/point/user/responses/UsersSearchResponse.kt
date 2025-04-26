package ru.point.user.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsersSearchResponse(
    @SerialName("in_contacts")
    val inContacts: List<UserInfoShort>,
    @SerialName("all_contacts")
    val allContacts: List<UserInfoShort>,
)