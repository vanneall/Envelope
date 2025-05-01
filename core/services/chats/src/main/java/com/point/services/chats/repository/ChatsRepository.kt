package com.point.services.chats.repository

import android.net.Uri
import com.point.services.chats.events.models.Event
import com.point.services.chats.models.ChatCreationData
import com.point.services.chats.models.ChatInfo
import com.point.services.chats.models.GroupChatInfo

interface ChatsRepository {

    suspend fun getChats(offset: Int = 0, limit: Int = 35): Result<List<ChatInfo>>

    suspend fun getChatEvents(chatId: String): Result<List<Event>>

    suspend fun createChat(data: ChatCreationData, uri: Uri? = null): Result<String>

    suspend fun getGroupChatInfo(chatId: String): Result<GroupChatInfo>

    suspend fun deleteUserFromChat(username: String, chatId: String): Result<Unit>

    suspend fun delete(id: String): Result<Unit>

    suspend fun delete(ids: List<String>): Result<Unit>
}