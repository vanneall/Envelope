package com.point.chats.dialog.data

import com.point.services.chats.events.models.Event
import com.point.services.chats.models.MessageCreate
import com.point.services.chats.models.MessageEdit
import com.point.services.chats.repository.ChatsRepository
import com.point.services.chats.repository.DialogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatDialogRepository @Inject constructor(
    private val dialogRepository: DialogRepository,
    private val chatsRepository: ChatsRepository,
) {

    fun connectToChat(chatId: String): Flow<Event> = dialogRepository.connect(chatId = chatId)

    fun sendMessage(text: String, photoIds: List<Long>) = dialogRepository.sendMessage(
        MessageCreate(
            content = text,
            photos = photoIds,
        )
    )

    fun editMessage(messageId: String, text: String) = dialogRepository.editMessage(MessageEdit(messageId, text))

    fun disconnect() = dialogRepository.disconnect()

    fun deleteMessage(id: String) = dialogRepository.deleteMessage(id)

    suspend fun fetchChatInfo(id: String) = withContext(Dispatchers.IO) {
        chatsRepository.getChats().map { it.first { it.id == id } }
    }

    suspend fun fetchInfo(id: String): Result<List<Event>> = withContext(Dispatchers.IO) {
        chatsRepository.getChatEvents(id)
    }
}