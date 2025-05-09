package ru.point.user.responses

import com.point.network.di.serializers.LocalDateSerializer
import com.point.network.di.serializers.LocalDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalDateTime

@Serializable
internal class DetailedUserProfileResponse(
    @SerialName("username")
    val username: String,
    @SerialName("name")
    val name: String,
    @Serializable(LocalDateTimeSerializer::class)
    @SerialName("last_seen")
    val lastSeen: LocalDateTime,
    @SerialName("status")
    val status: String?,
    @SerialName("about")
    val about: String?,
    @Serializable(LocalDateSerializer::class)
    @SerialName("birth_date")
    val birthDate: LocalDate,
    @SerialName("photos")
    val photos: List<String>,
    @SerialName("friends_count")
    val friendsCount: Int,
    @SerialName("email")
    val email: String,
    @SerialName("friends")
    val contacts: List<OtherUserResponse>,
    @SerialName("blocked_count")
    val blockedCount: Int,
    @SerialName("blocked_users")
    val blockedUsers: List<OtherUserResponse>,
)
