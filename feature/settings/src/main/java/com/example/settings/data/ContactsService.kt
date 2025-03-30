package com.example.settings.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

interface ContactsService {
    @GET("/users/api-v2")
    suspend fun fetchUserData(): Result<UserProfileDetailedResponse>

    @Multipart
    @PUT("/users/api-v2")
    suspend fun putUserData(
        @Part("data") data: UserProfileUpdateRequest,
    ): Result<UserProfileDetailedResponse>
}

@Serializable
data class UserProfileDetailedResponse(
    @SerialName("username")
    val username: String,
    @SerialName("name")
    val name: String,
    @SerialName("status")
    val status: String?,
    @SerialName("about")
    val about: String?,
)

@Serializable
data class UserProfileUpdateRequest(
    @SerialName("name")
    val name: String? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("about")
    val about: String? = null,
)