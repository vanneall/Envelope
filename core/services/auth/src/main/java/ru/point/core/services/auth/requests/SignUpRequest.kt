package ru.point.core.services.auth.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.point.core.services.auth.models.SignupData
import java.time.LocalDate

@Serializable
internal data class SignUpRequest(
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

internal fun SignupData.toRequest() = SignUpRequest(
    username = login,
    password = password,
    name = name,
    birthDate = LocalDate.now().toString(),
    status = status,
    aboutUser = about,
    isDeveloper = true,
)