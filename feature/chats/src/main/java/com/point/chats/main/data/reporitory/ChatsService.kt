package com.point.chats.main.data.reporitory

import com.point.chats.dialog.data.events.BaseEvent
import com.point.chats.main.data.entity.requests.DeleteChatsRequest
import com.point.chats.main.data.entity.response.ChatInfoShort
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatsService {

    @GET("/chats/api-v2")
    suspend fun getChats(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Result<List<ChatInfoShort>>

    @GET("/chats/api-v2/{id}")
    suspend fun fetchEvents(@Path("id") id: String): Result<List<BaseEvent>>

    @DELETE("/chats/api-v2/{id}")
    suspend fun deleteChatById(@Path("id") id: String): Result<Unit>

    @DELETE("/chats/api-v2")
    suspend fun deleteChats(@Body deleteChatsRequest: DeleteChatsRequest) : Result<Unit>
}