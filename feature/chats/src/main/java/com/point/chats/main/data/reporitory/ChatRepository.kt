package com.point.chats.main.data.reporitory

import com.point.chats.main.data.entity.response.ChatInfoShort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatRepository @Inject constructor(private val chatsService: ChatsService) {

    suspend fun fetchChats(limit: Int = 20, offset: Int = 0): Result<List<ChatInfoShort>> =
        withContext(Dispatchers.IO) {
            chatsService.getChats(limit = limit, offset = offset)
        }

}