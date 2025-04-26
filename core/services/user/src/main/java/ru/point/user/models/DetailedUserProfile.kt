package ru.point.user.models

import ru.point.user.responses.DetailedUserProfileResponse
import java.time.LocalDate
import java.time.LocalDateTime

data class DetailedUserProfile(
    val username: String,
    val name: String,
    val lastSeen: LocalDateTime,
    val status: String?,
    val about: String?,
    val birthDate: LocalDate,
    val photos: List<String>,
    val friendsCount: Int,
    val contacts: List<OtherUser>,
    val blockedCount: Int,
    val blockedUsers: List<OtherUser>,
)

internal fun DetailedUserProfileResponse.toModel() = DetailedUserProfile(
    username = username,
    name = name,
    lastSeen = lastSeen,
    status = status,
    about = about,
    birthDate = birthDate,
    photos = photos,
    friendsCount = friendsCount,
    contacts = contacts.map { contact -> contact.toModel() },
    blockedCount = blockedCount,
    blockedUsers = blockedUsers.map { contact -> contact.toModel() },
)
