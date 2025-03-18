package com.point.chats.main.data.reporitory

import com.point.chats.main.data.entity.response.ChatInfoShort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ChatRepository {
    
    suspend fun fetchChats(limit: Int = 20, offset: Int = 0): Result<List<ChatInfoShort>>
    
}