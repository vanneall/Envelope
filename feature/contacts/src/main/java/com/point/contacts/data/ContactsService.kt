package com.point.contacts.data

import com.point.contacts.data.response.UserInfoShort
import retrofit2.http.GET
import retrofit2.http.Query

interface ContactsService {

    @GET("/users/api-v2/friends")
    suspend fun fetchFriends(): Result<List<UserInfoShort>>

    @GET("/users/api-v2/all")
    suspend fun fetchUsersByName(@Query("name") query: String): Result<List<UserInfoShort>>

}