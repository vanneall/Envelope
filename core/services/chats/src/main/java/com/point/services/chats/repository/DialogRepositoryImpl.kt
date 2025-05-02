package com.point.services.chats.repository

import com.point.network.di.TokenProvider
import com.point.network.di.websocket.STOMPUtils
import com.point.services.chats.events.models.Event
import com.point.services.chats.events.responses.BaseEventResponse
import com.point.services.chats.models.MessageCreate
import com.point.services.chats.models.MessageEdit
import com.point.services.chats.requests.MessageDeleteRequest
import com.point.services.chats.requests.toRequest
import com.point.services.chats.serializers.globalJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import timber.log.Timber

internal class DialogRepositoryImpl(private val okHttpClient: OkHttpClient, private val tokenProvider: TokenProvider) :
    DialogRepository {

    private var webSocket: WebSocket? = null

    private var chatId = ""
    override fun connect(chatId: String): Flow<Event> = callbackFlow {
        this@DialogRepositoryImpl.chatId = chatId

        val request = Request.Builder()
            .addHeader("Authorization", "Bearer ${tokenProvider.token.orEmpty()}")
            .url(WS_ENDPOINT)
            .build()

        webSocket = okHttpClient.newWebSocket(request, object : WebSocketListener() {

            override fun onOpen(webSocket: WebSocket, response: Response) {
                webSocket.send(STOMPUtils.connect())
                webSocket.send(STOMPUtils.subscribe(destination = "/topic/chat/$chatId", id = chatId))

                Timber.tag(WEBSOCKET_TAG).d("Connected")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                if (!text.startsWith("MESSAGE")) return
                try {
                    val json = parseStompResponse(text)
                    val event = globalJson.decodeFromString<BaseEventResponse>(json)
                    trySend(event.toModel())
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

//    private fun sendStompConnect() {
//        val connectMessage = """
//            CONNECT
//            accept-version:1.1,1.2
//            heart-beat:10000,10000
//
//            ${"\u0000"}
//        """.trimIndent()
//        webSocket?.send(connectMessage)
//    }
//
//    private fun subscribeToChat(chatId: String) {
//        val subscribeMessage = """
//        SUBSCRIBE
//        id:sub-chat-$chatId
//        destination:/topic/chat/$chatId
//
//        ${"\u0000"}
//        """.trimIndent()
//        webSocket?.send(subscribeMessage)
//    }
//
//    private fun sendMessage(chatId: String, json: String) {
//        val stompMessage = """
//        SEND
//        destination:/app/chat/$chatId/sendMessage
//
//        $json
//        ${"\u0000"}
//        """.trimIndent()
//
//        webSocket?.send(stompMessage)
//    }
//
//private fun deleteMessage(chatId: String, json: String) {
//    val stompMessage = """
//        SEND
//        destination:/app/chat/$chatId/deleteMessage
//
//        $json
//        ${"\u0000"}
//        """.trimIndent()
//
//    webSocket?.send(stompMessage)
//}
//
//    private fun editMessage(chatId: String, json: String) {
//        val stompMessage = """
//        SEND
//        destination:/app/chat/$chatId/editMessage
//
//        $json
//        ${"\u0000"}
//        """.trimIndent()
//
//        webSocket?.send(stompMessage)
//    }

    override fun sendMessage(message: MessageCreate) {
        val messageJson = Json.encodeToString(message.toRequest())
        webSocket?.send(STOMPUtils.send(destination = "/app/chat/$chatId/sendMessage", json = messageJson))
    }

    override fun deleteMessage(id: String) {
        val deleteMessageJson = Json.encodeToString(MessageDeleteRequest(id = id))
        webSocket?.send(STOMPUtils.send(destination = "/app/chat/$chatId/deleteMessage", json = deleteMessageJson))
    }

    override fun editMessage(messageEdit: MessageEdit) {
        val messageEditJson = Json.encodeToString(messageEdit.toRequest())
        webSocket?.send(STOMPUtils.send(destination = "/app/chat/$chatId/editMessage", json = messageEditJson))
    }

    override fun disconnect() {
        webSocket?.close(1000, "Goodbye")
        webSocket = null
    }

    private companion object {

        const val WS_ENDPOINT = "ws://192.168.0.174:8082/ws"

        const val WEBSOCKET_TAG = "Websocket"
    }
}