package ru.point.user.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.point.user.requests.CreateChatRequest
import ru.point.user.requests.FriendRequest
import ru.point.user.services.UserService
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(private val userService: UserService) : UserRepository {

    override suspend fun fetchUserContacts() = withContext(Dispatchers.IO) {
        userService.fetchFriends()
    }

    override suspend fun fetchUsersByName(query: String) = withContext(Dispatchers.IO) {
        userService.fetchUsersByName(query = query)
    }

    override suspend fun fetchIncomingRequests() = withContext(Dispatchers.IO) {
        userService.fetchIncomingRequest(isIncoming = true)
    }

    override suspend fun acceptRequest(userId: String) = withContext(Dispatchers.IO) {
        userService.patchApproveRequest(FriendRequest(otherId = userId))
    }

    override suspend fun denyRequest(userId: String) = withContext(Dispatchers.IO) {
        userService.patchRejectRequest(FriendRequest(otherId = userId))
    }

    override suspend fun sendRequest(userId: String) = withContext(Dispatchers.IO) {
        userService.patchSendRequest(FriendRequest(otherId = userId))
    }

    override suspend fun fetchUserDataShort(username: String) = withContext(Dispatchers.IO) {
        userService.fetchUserInfoShort(username = username)
    }

    override suspend fun deleteUserFromFriends(username: String) = withContext(Dispatchers.IO) {
        userService.deleteUserFromFriends(username)
    }

    override suspend fun getChatId(username: String) = withContext(Dispatchers.IO) {
        userService.createChat(CreateChatRequest(listOf(username)))
    }
}