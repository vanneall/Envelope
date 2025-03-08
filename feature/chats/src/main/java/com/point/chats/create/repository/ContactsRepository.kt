package com.point.chats.create.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactsRepository @Inject constructor(private val contactsService: ContactsService) {

    suspend fun fetchContacts(name: String = ""): Result<List<ContactResponse>> = withContext(Dispatchers.IO) {
        contactsService.fetchContacts(name = name)
    }
}