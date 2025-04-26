package ru.point.user.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class UserInfoShortResponse(
    @SerialName("username")
    val username: String,
    @SerialName("name")
    val name: String,
    @SerialName("status")
    val status: String?,
    @SerialName("last_photo")
    val photo: Long?,
    @SerialName("in_contacts")
    val inContacts: Boolean,
    @SerialName("in_sent_requests")
    val inSentRequests: Boolean,
)
