package com.point.auth.common.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
)
