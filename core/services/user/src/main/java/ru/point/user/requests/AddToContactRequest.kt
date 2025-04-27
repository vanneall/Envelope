package ru.point.user.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class AddToContactRequest(
    @SerialName("user_id")
    val username: String,
)
