package com.point.contacts.data

import com.point.contacts.data.response.UserInfoShort
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface ContactsService {

    @GET("/users/api-v2/friends")
    suspend fun fetchFriends(): Result<List<UserInfoShort>>

    @GET("/users/api-v2/all")
    suspend fun fetchUsersByName(@Query("name") query: String): Result<List<UserInfoShort>>

    @GET("/users/api-v2/requests")
    suspend fun fetchIncomingRequest(@Query("incoming") isIncoming: Boolean = true): Result<List<UserInfoShort>>

    @PATCH("/users/api-v2/requests/approve")
    suspend fun patchApproveRequest(@Body request: FriendRequest): Result<Unit>

    @PATCH("/users/api-v2/requests/reject")
    suspend fun patchRejectRequest(@Body request: FriendRequest): Result<Unit>

    @PATCH("/users/api-v2/requests")
    suspend fun patchSendRequest(@Body request: FriendRequest): Result<Unit>

    @GET("/users/api-v2/{id}")
    suspend fun fetchUserInfoShort(@Path("id") username: String): Result<UserInfoShortResponse>

    @DELETE("/users/api-v2/friends/{username}")
    suspend fun deleteUserFromFriends(@Path("username") username: String): Result<Unit>

}

@Serializable
data class FriendRequest(
    @SerialName("friend_user_id")
    val otherId: String,
)

@Serializable
data class UserInfoShortResponse(
    @SerialName("username")
    val username: String,
    @SerialName("name")
    val name: String,
    @SerialName("status")
    val status: String?,
    @SerialName("about")
    val about: String?,
    @SerialName("photos")
    val photos: List<Long>,
    @SerialName("in_contacts")
    val inContacts: Boolean,
    @SerialName("in_sent_requests")
    val inSentRequests: Boolean,
)
