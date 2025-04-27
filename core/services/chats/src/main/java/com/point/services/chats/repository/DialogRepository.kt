package com.point.services.chats.repository

import com.point.services.chats.events.models.Event
import com.point.services.chats.models.MessageCreate
import com.point.services.chats.models.MessageEdit
import kotlinx.coroutines.flow.Flow

interface DialogRepository {

    fun connect(chatId: String): Flow<Event>

    fun disconnect()

    fun sendMessage(message: MessageCreate)

    fun deleteMessage(id: String)

    fun editMessage(message: MessageEdit)
}