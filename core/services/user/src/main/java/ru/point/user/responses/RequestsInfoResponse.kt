package ru.point.user.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class RequestsInfoResponse(
    @SerialName("username")
    val username: String,
    @SerialName("name")
    val name: String,
    @SerialName("status")
    val status: String?,
    @SerialName("last_photo")
    val lastPhoto: Long?,
)
