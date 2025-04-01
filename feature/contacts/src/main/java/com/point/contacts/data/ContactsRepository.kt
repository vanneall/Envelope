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

    suspend fun fetchIncomingRequests() = withContext(Dispatchers.IO) {
        contactsService.fetchIncomingRequest(isIncoming = true)
    }

    suspend fun acceptRequest(userId: String) = withContext(Dispatchers.IO) {
        contactsService.patchApproveRequest(FriendRequest(otherId = userId))
    }

    suspend fun denyRequest(userId: String) = withContext(Dispatchers.IO) {
        contactsService.patchRejectRequest(FriendRequest(otherId = userId))
    }

    suspend fun sendRequest(userId: String) = withContext(Dispatchers.IO) {
        contactsService.patchSendRequest(FriendRequest(otherId = userId))
    }

    suspend fun fetchUserDataShort(username: String) = withContext(Dispatchers.IO) {
        contactsService.fetchUserInfoShort(username = username)
    }

    suspend fun deleteUserFromFriends(username: String) = withContext(Dispatchers.IO) {
        contactsService.deleteUserFromFriends(username)
    }

    suspend fun getChatId(username: String) = withContext(Dispatchers.IO) {
        contactsService.createChat(CreateChatRequest(listOf(username)))
    }
}