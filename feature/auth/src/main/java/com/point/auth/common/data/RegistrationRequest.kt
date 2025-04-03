package com.point.auth.common.data

import com.point.auth.registration.presenter.host.UserRegistrationData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
internal data class UserRegistrationRequest(
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

internal fun UserRegistrationData.toUserRegistrationRequest() = UserRegistrationRequest(
    username = login,
    password = password,
    name = name,
    birthDate = LocalDate.now().toString(),
    status = status,
    aboutUser = about,
    isDeveloper = true,
)