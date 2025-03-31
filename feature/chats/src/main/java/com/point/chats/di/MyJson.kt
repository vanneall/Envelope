package com.point.chats.di

import com.point.chats.dialog.data.events.BaseEvent
import com.point.chats.dialog.data.events.MessageSentEvent
import com.point.chats.dialog.data.events.UserJoinedEvent
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

val globalJson = Json {
    ignoreUnknownKeys = true
    prettyPrint = true
    isLenient = true
    classDiscriminator = "_class"
    serializersModule = SerializersModule {
        polymorphic(BaseEvent::class) {
            subclass(MessageSentEvent::class, MessageSentEvent.serializer())
            subclass(UserJoinedEvent::class, UserJoinedEvent.serializer())
        }
    }
}