package com.point.chats.dialog.network

import com.point.chats.di.globalJson
import com.point.chats.dialog.data.events.BaseEvent
import com.point.network.di.TokenProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import timber.log.Timber
import javax.inject.Inject

class ChatDialogWebsocketClient @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val tokenProvider: TokenProvider,
) {
    private var webSocket: WebSocket? = null

    private var chatId = ""
    fun connect(chatId: String): Flow<BaseEvent> = callbackFlow {
        this@ChatDialogWebsocketClient.chatId = chatId

        val request = Request.Builder()
            .addHeader("Authorization", "Bearer ${tokenProvider.token.orEmpty()}")
            .url(WS_ENDPOINT)
            .build()

        webSocket = okHttpClient.newWebSocket(request, object : WebSocketListener() {

            override fun onOpen(webSocket: WebSocket, response: Response) {
                sendStompConnect()
                subscribeToChat(chatId)
                Timber.tag(WEBSOCKET_TAG).d("Connected")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                if (!text.startsWith("MESSAGE")) return
                try {
                    val json = parseStompResponse(text)
                    val event = globalJson.decodeFromString<BaseEvent>(json)
                    trySend(event)
                } catch (e: Exception) {
                    Timber.tag(WEBSOCKET_TAG).e("onMessage error: ${e.message}")
                }
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Timber.tag(WEBSOCKET_TAG).e("onCloses")
                close()
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Timber.tag(WEBSOCKET_TAG).e("onFailure: ${t.stackTraceToString()}")
                close(t)
            }
        })

        awaitClose { webSocket?.close(1000, "Closing WebSocket") }
    }.flowOn(Dispatchers.IO)

    private fun parseStompResponse(text: String): String {
        val jsonStartIndex = text.indexOfFirst { it == '{' }
        val jsonEndIndex = text.indexOfFirst { it == '}' } + 1
        return text.substring(jsonStartIndex, jsonEndIndex)
    }

    private fun sendStompConnect() {
        val connectMessage = """
            CONNECT
            accept-version:1.1,1.2
            heart-beat:10000,10000
            
            ${"\u0000"}
        """.trimIndent()
        webSocket?.send(connectMessage)
    }

    private fun subscribeToChat(chatId: String) {
        val subscribeMessage = """
        SUBSCRIBE
        id:sub-chat-$chatId
        destination:/topic/chat/$chatId
        
        ${"\u0000"}
        """.trimIndent()
        webSocket?.send(subscribeMessage)
    }

    fun sendMessage(request: CreateMessageRequest) {
        sendMessage(chatId = chatId, json = Json.encodeToString(request))
    }

    private fun sendMessage(chatId: String, json: String) {
        val stompMessage = """
        SEND
        destination:/app/chat/$chatId/sendMessage
        
        $json
        ${"\u0000"}
        """.trimIndent()

        webSocket?.send(stompMessage)
    }

    fun deleteMessage(request: DeleteMessage) {
        deleteMessage(chatId = chatId, json = Json.encodeToString(request))
    }

    private fun deleteMessage(chatId: String, json: String) {
        val stompMessage = """
        SEND
        destination:/app/chat/$chatId/deleteMessage
        
        $json
        ${"\u0000"}
        """.trimIndent()

        webSocket?.send(stompMessage)
    }

    fun editMessage(editMessage: EditMessage) {
        editMessage(chatId, Json.encodeToString(editMessage))
    }

    private fun editMessage(chatId: String, json: String) {
        val stompMessage = """
        SEND
        destination:/app/chat/$chatId/editMessage
        
        $json
        ${"\u0000"}
        """.trimIndent()

        webSocket?.send(stompMessage)
    }


    fun disconnect() {
        webSocket?.close(1000, "Goodbye")
        webSocket = null
    }

    private companion object {

        const val WS_ENDPOINT = "ws://192.168.0.174:8082/ws"

        const val WEBSOCKET_TAG = "Websocket"
    }
}

@Serializable
data class CreateMessageRequest(
    @SerialName("content")
    val content: String,
    @SerialName("photos")
    val photos: List<Long>,
)

@Serializable
data class DeleteMessage(
    @SerialName("message_id")
    val messageId: String
)

@Serializable
data class EditMessage(
    @SerialName("message_id")
    val messageId: String,
    @SerialName("content")
    val content: String,
)
