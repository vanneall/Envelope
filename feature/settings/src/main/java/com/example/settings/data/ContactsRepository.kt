package com.example.settings.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactsRepository @Inject constructor(private val contactsService: ContactsService) {

    suspend fun fetchUserData() = withContext(Dispatchers.IO) {
        contactsService.fetchUserData()
    }

    suspend fun saveUserData(data: UserProfileUpdateRequest) = withContext(Dispatchers.IO) {
        contactsService.putUserData(data)
    }
}