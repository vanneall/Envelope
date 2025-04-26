package ru.point.core.services.auth.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.point.core.services.auth.models.SigninData

@Serializable
internal data class SignInRequest(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
)

internal fun SigninData.toRequest() = SignInRequest(
    username = username,
    password = password,
)