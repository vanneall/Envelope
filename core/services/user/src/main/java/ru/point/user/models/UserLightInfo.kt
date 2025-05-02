package ru.point.user.models

import ru.point.user.responses.UserLightInfoResponse

data class UserLightInfo(
    val username: String,
    val name: String,
    val photo: String? = null,
)

internal fun UserLightInfoResponse.toModel() = UserLightInfo(
    username = username,
    name = name,
    photo = photoId?.let { uri -> "http://192.168.0.174:8084/media/$uri" },
)