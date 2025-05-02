package com.point.services.chats.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageCreate(val content: String, @SerialName("photos") val photos: List<Long>)