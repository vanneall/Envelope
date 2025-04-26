package ru.point.user.services

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.point.user.requests.CreateChatRequest
import ru.point.user.requests.FriendRequest
import ru.point.user.responses.ChatIdResponse
import ru.point.user.responses.UserInfoShort
import ru.point.user.responses.UserInfoShortResponse
import ru.point.user.responses.UsersSearchResponse

internal interface UserService {

    @GET("$BASE_URL/contacts")
    suspend fun fetchFriends(): Result<List<UserInfoShort>>

    @GET("$BASE_URL/all")
    suspend fun fetchUsersByName(@Query("name") query: String): Result<UsersSearchResponse>

    @GET("$BASE_URL/requests")
    suspend fun fetchIncomingRequest(@Query("incoming") isIncoming: Boolean = true): Result<List<UserInfoShort>>

    @PATCH("$BASE_URL/requests/approve")
    suspend fun patchApproveRequest(@Body request: FriendRequest): Result<Unit>

    @PATCH("$BASE_URL/requests/reject")
    suspend fun patchRejectRequest(@Body request: FriendRequest): Result<Unit>

    @PATCH("$BASE_URL/requests")
    suspend fun patchSendRequest(@Body request: FriendRequest): Result<Unit>

    @GET("$BASE_URL/{id}")
    suspend fun fetchUserInfoShort(@Path("id") username: String): Result<UserInfoShortResponse>

    @DELETE("$BASE_URL/contacts/{username}")
    suspend fun deleteUserFromFriends(@Path("username") username: String): Result<Unit>

    @POST("/chats/api-v2")
    suspend fun createChat(@Body metadata: CreateChatRequest): Result<ChatIdResponse>
}

private const val BASE_URL = "BASE_URL"