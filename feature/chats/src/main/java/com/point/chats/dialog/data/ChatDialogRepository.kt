package com.point.chats.dialog.data

import com.point.chats.dialog.data.events.BaseEvent
import com.point.chats.dialog.network.ChatDialogWebsocketClient
import com.point.chats.dialog.network.CreateMessageRequest
import com.point.chats.dialog.network.DeleteMessage
import com.point.chats.dialog.network.EditMessage
import com.point.chats.main.data.reporitory.ChatsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatDialogRepository @Inject constructor(
    private val webSocketClient: ChatDialogWebsocketClient,
    private val dialogService: ChatsService,
) {

    fun connectToChat(chatId: String): Flow<BaseEvent> = webSocketClient.connect(chatId = chatId)

    fun sendMessage(text: String, photoIds: List<Long>) = webSocketClient.sendMessage(
        CreateMessageRequest(
            content = text,
            photos = photoIds,
        )
    )

    fun editMessage(messageId: String, text: String) = webSocketClient.editMessage(EditMessage(messageId, text))

    fun disconnect() = webSocketClient.disconnect()

    fun deleteMessage(id: String) = webSocketClient.deleteMessage(
        DeleteMessage(messageId = id)
    )

    suspend fun fetchChatInfo(id: String) = withContext(Dispatchers.IO) {
        dialogService.getChats(100, 0).map { it.first { it.id == id } }
    }

    suspend fun fetchInfo(id: String): Result<List<BaseEvent>> = withContext(Dispatchers.IO) {
        dialogService.fetchEvents(id)
    }
}