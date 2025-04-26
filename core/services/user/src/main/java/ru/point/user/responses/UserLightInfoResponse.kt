package ru.point.user.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class UserLightInfoResponse(
    @SerialName("username")
    val username: String,
    @SerialName("name")
    val name: String,
    @SerialName("photo")
    val photoId: Long? = null,
)