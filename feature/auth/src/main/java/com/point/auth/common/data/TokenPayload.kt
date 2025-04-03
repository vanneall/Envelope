package com.point.auth.common.data

import com.point.user.token.Token
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenPayload(
    @SerialName("access_token")
    val token: Token,
)