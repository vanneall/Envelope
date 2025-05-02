package ru.point.user.models

import ru.point.user.responses.UserInfoResponse
import java.time.LocalDate
import java.time.LocalDateTime

data class UserInfo(
    val username: String,
    val name: String,
    val lastSeen: LocalDateTime,
    val status: String?,
    val about: String?,
    val birthDate: LocalDate,
    val photos: List<String>,
    val inContacts: Boolean,
    val inSentRequests: Boolean,
)

internal fun UserInfoResponse.toModel() = UserInfo(
    username = username,
    name = name,
    lastSeen = lastSeen,
    status = status,
    about = about,
    birthDate = birthDate,
    photos = photos.map { uri -> "http://192.168.0.174:8084/media/$uri" },
    inContacts = inContacts,
    inSentRequests = inSentRequests
)