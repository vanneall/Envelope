package com.point.chats.main.data.reporitory

import com.point.chats.main.data.entity.response.ChatInfoShort
import javax.inject.Inject

class ChatRepositoryFake @Inject constructor(): ChatRepository {

    override suspend fun fetchChats(limit: Int, offset: Int): Result<List<ChatInfoShort>> {
        val chats = listOf(
            ChatInfoShort(
                id = "fake_id_1",
                name = "Name 1",
                lastMessage = "my message",
            ),
            ChatInfoShort(
                id = "fake_id_2",
                name = "Name 2",
                lastMessage = "short message",
            ),
            ChatInfoShort(
                id = "fake_id_3",
                name = "Name 3",
                lastMessage = "maybe medium message",
            ),
            ChatInfoShort(
                id = "fake_id_4",
                name = "Name 4",
                lastMessage = "i'm not sure that this is a large message",
            ),
            ChatInfoShort(
                id = "fake_id_5",
                name = "Name 5",
                lastMessage = "i can't believe that i created this very very very long message only for test",
            ),
        )
        
        return Result.success(chats)
    }
}