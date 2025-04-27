package com.point.services.chats.events.responses

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

internal object BaseEventSerializer : JsonContentPolymorphicSerializer<BaseEventResponse>(BaseEventResponse::class) {

    override fun selectDeserializer(element: JsonElement): KSerializer<out BaseEventResponse> {
        val type = element.jsonObject["_class"]?.jsonPrimitive?.content
            ?: throw IllegalStateException("Missing _class field for BaseEvent deserialization")

        return when (type) {
            MessageSentEventResponse.DISCRIMINATOR -> MessageSentEventResponse.serializer()
            UserJoinedEventResponse.DISCRIMINATOR -> UserJoinedEventResponse.serializer()
            DeleteMessageEventResponse.DISCRIMINATOR -> DeleteMessageEventResponse.serializer()
            MessageEditedEventResponse.DISCRIMINATOR -> MessageEditedEventResponse.serializer()
            else -> throw IllegalArgumentException("Unknown _class type: $type")
        }
    }
}