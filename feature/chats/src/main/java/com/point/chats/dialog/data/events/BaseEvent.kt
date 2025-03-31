package com.point.chats.dialog.data.events

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import java.time.Instant

@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("_class")
@Serializable(with = BaseEventSerializer::class)
sealed interface BaseEvent {
    val id: String
    val timestamp: Instant
}