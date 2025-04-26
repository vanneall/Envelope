package ru.point.user.repository

import ru.point.user.responses.ChatIdResponse
import ru.point.user.responses.UserInfoShort
import ru.point.user.responses.UserInfoShortResponse
import ru.point.user.responses.UsersSearchResponse

interface UserRepository {

    suspend fun fetchUserContacts(): Result<List<UserInfoShort>>

    suspend fun fetchUsersByName(query: String): Result<UsersSearchResponse>

    suspend fun fetchIncomingRequests(): Result<List<UserInfoShort>>

    suspend fun acceptRequest(userId: String): Result<Unit>

    suspend fun denyRequest(userId: String): Result<Unit>

    suspend fun sendRequest(userId: String): Result<Unit>

    suspend fun fetchUserDataShort(username: String): Result<UserInfoShortResponse>

    suspend fun deleteUserFromFriends(username: String): Result<Unit>

    suspend fun getChatId(username: String): Result<ChatIdResponse>
}