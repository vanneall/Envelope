package com.point.chats.main.data.reporitory

import com.point.chats.main.data.entity.response.ChatInfoShort
import retrofit2.http.GET
import retrofit2.http.Query

interface ChatsService {

    @GET("/chats")
    suspend fun getChats(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Result<List<ChatInfoShort>>

}