package com.point.services.chats.events.responses

import com.point.services.chats.events.models.Event
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import java.time.Instant

@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("_class")
@Serializable(with = BaseEventSerializer::class)
internal sealed interface BaseEventResponse {
    val id: String
    val timestamp: Instant

    fun toModel(): Event
}