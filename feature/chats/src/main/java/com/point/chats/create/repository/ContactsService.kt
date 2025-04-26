package com.point.chats.create.repository

import com.point.chats.creation.data.UserInfoShort
import com.point.chats.multi.info.data.GroupChatInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ContactsService {

    @GET("/users/api-v2/contacts")
    suspend fun fetchContacts(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
        @Query("name") name: String = "",
    ): Result<List<UserInfoShort>>

    @POST("/chats/api-v2")
    suspend fun createChat(@Body metadata: CreateChatRequest): Result<ChatIdResponse>

    @GET("/chats/api-v2/group/{chatId}")
    suspend fun getGroupChatInfo(@Path("chatId") chatId: String): Result<GroupChatInfo>

    @DELETE("/chats/api-v2/group/{chatId}/{username}")
    suspend fun deleteUserFromChat(@Path("chatId") chatId: String, @Path("username") username: String)
}

@Serializable
data class CreateChatRequest(
    @SerialName("participants")
    val participantIds: List<String>,
    @SerialName("name")
    val name: String? = null,
)


@Serializable
data class ChatIdResponse(
    @SerialName("id")
    val id: String,
)