package com.point.contacts.search.data

import com.point.contacts.search.data.request.AddContactRequest
import com.point.contacts.search.data.response.UserInfoShort
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ContactsService2 {

    @GET("/users")
    suspend fun fetchContactsByName(@Query("name") name: String): Result<List<UserInfoShort>>

    @POST("/users/contacts")
    suspend fun postNewContact(@Body request: AddContactRequest): Result<Unit>

}