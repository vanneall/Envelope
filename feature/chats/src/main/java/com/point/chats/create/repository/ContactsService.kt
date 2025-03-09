package com.point.chats.create.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ContactsService {

    @GET("/users/contacts")
    suspend fun fetchContacts(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
        @Query("name") name: String = "",
    ): Result<List<ContactResponse>>

    @Multipart
    @POST("/chats")
    suspend fun createChat(
        @Part("name") name: String,
        @Part("description") description: String?,
        @Part("participantId") participantId: String,
    ): Result<CreateChatResponse>

}