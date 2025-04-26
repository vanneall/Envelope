package com.point.chats.create.repository

import com.point.chats.multi.info.data.GroupChatInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatsRepository @Inject constructor(private val contactsService: ContactsService) {

    suspend fun fetchContacts(name: String = "") = withContext(Dispatchers.IO) {
        contactsService.fetchContacts(name = name)
    }

    suspend fun createChat(metadata: CreateChatRequest): Result<ChatIdResponse> = withContext(Dispatchers.IO) {
        contactsService.createChat(metadata)
    }

    suspend fun getGroupChatInfo(chatId: String): Result<GroupChatInfo> = withContext(Dispatchers.IO) {
        contactsService.getGroupChatInfo(chatId)
    }

    suspend fun deleteUserFromChat(chatId: String, userId: String) = withContext(Dispatchers.IO) {
        contactsService.deleteUserFromChat(chatId, userId)
    }
}