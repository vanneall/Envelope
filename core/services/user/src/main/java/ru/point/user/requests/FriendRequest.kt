package ru.point.user.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FriendRequest(
    @SerialName("friend_user_id")
    val otherId: String,
)