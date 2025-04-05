package com.point.chats.dialog.data.events

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object BaseEventSerializer : JsonContentPolymorphicSerializer<BaseEvent>(BaseEvent::class) {
    override fun selectDeserializer(element: JsonElement): KSerializer<out BaseEvent> {
        val type = element.jsonObject["_class"]?.jsonPrimitive?.content
            ?: throw IllegalStateException("Missing _class field for BaseEvent deserialization")
        return when (type) {
            "message" -> MessageSentEvent.serializer()
            "notification" -> UserJoinedEvent.serializer()
            "message_delete" -> DeleteMessageEvent.serializer()
            "message_edit" -> MessageEditedEvent.serializer()
            else -> throw IllegalArgumentException("Unknown _class type: $type")
        }
    }
}
