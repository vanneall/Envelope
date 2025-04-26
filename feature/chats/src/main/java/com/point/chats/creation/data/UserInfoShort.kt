package com.point.chats.creation.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoShort(
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