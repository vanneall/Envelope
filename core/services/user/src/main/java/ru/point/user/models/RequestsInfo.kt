package ru.point.user.models

import ru.point.user.responses.RequestsInfoResponse

data class RequestsInfo(
    val username: String,
    val name: String,
    val status: String?,
    val lastPhoto: String?,
)

internal fun RequestsInfoResponse.toModel() = RequestsInfo(
    username = username,
    name = name,
    status = status,
    lastPhoto = lastPhoto?.let { photoId -> "http://192.168.0.174:8084/photos/$photoId" },
)
