package com.point.auth.authorization.token

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Token(
    @SerialName("access_token")
    val token: String,
)