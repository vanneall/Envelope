package com.point.services.chats.services

import com.point.services.chats.events.responses.BaseEventResponse
import com.point.services.chats.requests.CreateChatRequest
import com.point.services.chats.requests.DeleteChatsRequest
import com.point.services.chats.responses.ChatIdResponse
import com.point.services.chats.responses.ChatInfoResponse
import com.point.services.chats.responses.GroupChatInfoResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ChatsService {

    @Multipart
    @POST(PATH)
    suspend fun createChat(
        @Part("data") data: CreateChatRequest,
        @Part photo: MultipartBody.Part?
    ): Result<ChatIdResponse>

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




