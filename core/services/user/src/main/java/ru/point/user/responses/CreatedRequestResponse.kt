package ru.point.user.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class CreatedRequestResponse(
    @SerialName("id")
    val id: Long,
)