package com.point.auth.authorization.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRegistrationRequest(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
    @SerialName("name")
    val name: String,
    @SerialName("status")
    val status: String? = null,
    @SerialName("about_user")
    val aboutUser: String? = null,
    @SerialName("birth_date")
    val birthDate: String,
    @SerialName("is_dev")
    val isDeveloper: Boolean = false
)