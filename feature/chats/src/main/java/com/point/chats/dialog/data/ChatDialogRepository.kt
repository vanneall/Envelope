package com.point.chats.dialog.data

import com.point.chats.dialog.data.events.BaseEvent
import com.point.chats.dialog.network.ChatDialogWebsocketClient
import com.point.chats.dialog.network.CreateMessageRequest
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

    fun sendMessage(text: String) = webSocketClient.sendMessage(
        CreateMessageRequest(
            content = text,
            photos = null,
        )
    )

    fun disconnect() = webSocketClient.disconnect()

    suspend fun fetchInfo(id: String): Result<List<BaseEvent>> = withContext(Dispatchers.IO) {
        dialogService.fetchEvents(id)
    }
}