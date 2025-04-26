package ru.point.core.services.auth.responses

import com.point.user.token.Token
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TokenResponse(
    @SerialName("access_token")
    val token: Token,
)
