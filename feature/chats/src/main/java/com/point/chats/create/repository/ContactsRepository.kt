package com.point.chats.create.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ContactsRepository @Inject constructor(private val contactsService: ContactsService) {

    suspend fun fetchContacts(name: String = ""): Result<List<ContactResponse>> = withContext(Dispatchers.IO) {
        contactsService.fetchContacts(name = name)
    }

    suspend fun createChatWithContact(id: String): Result<CreateChatResponse> = withContext(Dispatchers.IO) {
        val namePart = "my chat".toRequestBody("text/plain".toMediaTypeOrNull())
        val participantPart = id.toRequestBody("text/plain".toMediaTypeOrNull())
        val descriptionPart = null

        contactsService.createChat(
            name = "my chat",
            description = null,
            participantId = id,
        )
    }
}