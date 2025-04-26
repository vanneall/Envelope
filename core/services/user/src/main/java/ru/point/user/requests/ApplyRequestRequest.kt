package ru.point.user.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.point.user.models.ApplyRequestAbility

@Serializable
internal class ApplyRequestRequest(
    @SerialName("result")
    val result: ApplyRequestAbility,
)