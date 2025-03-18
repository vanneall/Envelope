package com.point.chats.main.data.reporitory

import com.point.chats.main.data.entity.response.ChatInfoShort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(private val chatsService: ChatsService): ChatRepository {

    override suspend fun fetchChats(limit: Int, offset: Int): Result<List<ChatInfoShort>> =
        withContext(Dispatchers.IO) {
            chatsService.getChats(limit = limit, offset = offset)
        }

}