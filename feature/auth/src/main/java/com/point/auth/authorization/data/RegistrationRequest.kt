package com.point.auth.authorization.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRegistrationRequest(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
    @SerialName("telephone")
    val telephone: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("is_dev")
    val isDeveloper: Boolean = true,
)