package com.point.chats.dialog.data

import com.point.chats.dialog.network.ChatDialogWebsocketClient
import com.point.chats.dialog.network.CreateMessageRequest
import com.point.chats.dialog.viewmodel.Message
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatDialogRepository @Inject constructor(private val webSocketClient: ChatDialogWebsocketClient) {

    fun connectToChat(chatId: String): Flow<Message> = webSocketClient.connect(chatId = chatId)

    fun sendMessage(text: String) = webSocketClient.sendMessage(
        CreateMessageRequest(
            content = text,
            photos = null,
        )
    )

    fun disconnect() = webSocketClient.disconnect()

}