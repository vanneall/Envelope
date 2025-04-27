package com.point.services.chats.repository

import com.point.services.chats.models.ChatCreationData
import com.point.services.chats.models.GroupChatInfo
import com.point.services.chats.models.toModel
import com.point.services.chats.requests.DeleteChatsRequest
import com.point.services.chats.requests.toRequest
import com.point.services.chats.services.ChatsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class ChatsRepositoryImpl(
    private val chatsService: ChatsService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ChatsRepository {

    override suspend fun createChat(data: ChatCreationData) = withContext(dispatcher) {
        chatsService.createChat(data = data.toRequest()).map { response -> response.id }
    }

    override suspend fun getGroupChatInfo(chatId: String): Result<GroupChatInfo> = withContext(dispatcher) {
        chatsService.getGroupChatInfo(chatId = chatId).map { response -> response.toModel() }
    }

    override suspend fun deleteUserFromChat(username: String, chatId: String) = withContext(dispatcher) {
        chatsService.deleteUserFromChat(chatId = chatId, username = username)
    }

    override suspend fun getChats(offset: Int, limit: Int) = withContext(dispatcher) {
        chatsService.getChats(offset = offset, limit = limit).map { response ->
            response.map { chats -> chats.toModel() }
        }
    }

    override suspend fun delete(id: String) = withContext(dispatcher) {
        chatsService.delete(id = id)
    }

    override suspend fun delete(ids: List<String>) = withContext(dispatcher) {
        chatsService.delete(deleteChatsRequest = DeleteChatsRequest(chatIds = ids))
    }

    override suspend fun getChatEvents(chatId: String) = withContext(dispatcher) {
        chatsService.getChatEvents(id = chatId).map { response ->
            response.map { event -> event.toModel() }
        }
    }
}