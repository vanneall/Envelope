package com.point.chats.create.repository

import retrofit2.http.GET
import retrofit2.http.Query

interface ContactsService {

    @GET("/users/contacts")
    suspend fun fetchContacts(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
        @Query("name") name: String = "",
    ): Result<List<ContactResponse>>

}