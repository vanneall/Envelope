package ru.point.user.models

import ru.point.user.requests.UserProfileUpdateRequest
import java.time.LocalDate

data class UserProfileUpdate(
    val name: String? = null,
    val status: String? = null,
    val about: String? = null,
    val birthDate: LocalDate? = null,
)

internal fun UserProfileUpdate.toRequest() = UserProfileUpdateRequest(
    name = name,
    status = status,
    about = about,
    birthDate = birthDate,
)
