package com.point.services.chats.serializers

import com.point.services.chats.events.responses.BaseEventResponse
import com.point.services.chats.events.responses.DeleteMessageEventResponse
import com.point.services.chats.events.responses.MessageEditedEventResponse
import com.point.services.chats.events.responses.MessageSentEventResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

val globalJson = Json {
    ignoreUnknownKeys = true
    prettyPrint = true
    isLenient = true
    classDiscriminator = "_class"
    serializersModule = SerializersModule {
        polymorphic(BaseEventResponse::class) {
            subclass(MessageSentEventResponse::class, MessageSentEventResponse.serializer())
            subclass(MessageSentEventResponse::class, MessageSentEventResponse.serializer())
            subclass(DeleteMessageEventResponse::class, DeleteMessageEventResponse.serializer())
            subclass(MessageEditedEventResponse::class, MessageEditedEventResponse.serializer())
        }
    }
}