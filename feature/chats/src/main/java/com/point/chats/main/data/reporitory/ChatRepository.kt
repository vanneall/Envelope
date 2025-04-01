package com.point.chats.main.data.reporitory

import com.point.chats.main.data.entity.response.ChatInfoShort

interface ChatRepository {
    
    suspend fun fetchChats(limit: Int = 20, offset: Int = 0): Result<List<ChatInfoShort>>

    suspend fun deleteDialog(id: String): Result<Unit>
    
}