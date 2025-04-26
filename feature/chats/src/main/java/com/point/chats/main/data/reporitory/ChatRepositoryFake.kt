package com.point.chats.main.data.reporitory

import com.point.chats.main.data.entity.response.ChatInfoShort
import javax.inject.Inject

class ChatRepositoryFake @Inject constructor(): ChatRepository {

    override suspend fun fetchChats(limit: Int, offset: Int): Result<List<ChatInfoShort>> {
        val chats = emptyList<ChatInfoShort>(
        )
        
        return Result.success(chats)
    }

    override suspend fun deleteDialog(id: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteChats(ids: List<String>): Result<Unit> {
        TODO("Not yet implemented")
    }
}