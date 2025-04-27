package com.point.services.chats.services

import com.point.services.chats.events.responses.BaseEventResponse
import com.point.services.chats.requests.CreateChatRequest
import com.point.services.chats.requests.DeleteChatsRequest
import com.point.services.chats.responses.ChatIdResponse
import com.point.services.chats.responses.ChatInfoResponse
import com.point.services.chats.responses.GroupChatInfoResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ChatsService {

    @POST(PATH)
    suspend fun createChat(@Body data: CreateChatRequest): Result<ChatIdResponse>

    // Group
    @GET("$PATH/group/{chatId}")
    suspend fun getGroupChatInfo(@Path("chatId") chatId: String): Result<GroupChatInfoResponse>

    @DELETE("$PATH/group/{chatId}/{username}")
    suspend fun deleteUserFromChat(@Path("chatId") chatId: String, @Path("username") username: String): Result<Unit>

    // Chats
    @GET(PATH)
    suspend fun getChats(@Query("limit") limit: Int, @Query("offset") offset: Int): Result<List<ChatInfoResponse>>

    @GET("$PATH/{id}")
    suspend fun getChatEvents(@Path("id") id: String): Result<List<BaseEventResponse>>

    @DELETE("$PATH/{id}")
    suspend fun delete(@Path("id") id: String): Result<Unit>

    @DELETE(PATH)
    suspend fun delete(@Body deleteChatsRequest: DeleteChatsRequest): Result<Unit>
}

private const val PATH = "/chats/api-v2"




