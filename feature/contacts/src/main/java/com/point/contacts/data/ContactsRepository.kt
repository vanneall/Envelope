package com.point.contacts.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactsRepository @Inject constructor(private val contactsService: ContactsService) {

    suspend fun fetchUserContacts() = withContext(Dispatchers.IO) {
        contactsService.fetchFriends()
    }

    suspend fun fetchUsersByName(query: String) = withContext(Dispatchers.IO) {
        contactsService.fetchUsersByName(query = query)
    }
}