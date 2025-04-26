package ru.point.user.services

import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import ru.point.user.requests.AddToContactRequest
import ru.point.user.requests.ApplyRequestRequest
import ru.point.user.requests.UserProfileUpdateRequest
import ru.point.user.responses.CreatedRequestResponse
import ru.point.user.responses.DetailedUserProfileResponse
import ru.point.user.responses.RequestsInfoResponse
import ru.point.user.responses.SearchUsersResponse
import ru.point.user.responses.UserContactResponse
import ru.point.user.responses.UserInfoResponse
import ru.point.user.responses.UserLightInfoResponse

internal interface UserService {

    // User contacts
    @GET(PATH_CONTACTS)
    suspend fun fetchUserContacts(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Result<List<UserContactResponse>>

    @DELETE("$PATH_CONTACTS/{id}")
    suspend fun deleteContactById(@Path("id") username: String): Result<Unit>

    // User profile
    @GET(PATH_PROFILE)
    suspend fun fetchUserDetailedInfo(): Result<DetailedUserProfileResponse>

    @Multipart
    @PATCH(PATH_PROFILE)
    suspend fun patchUser(
        @Part("data") data: UserProfileUpdateRequest,
        @Part photo: MultipartBody.Part? = null,
    ): Result<Unit>

    // User requests
    @GET("$PATH_REQUESTS/incoming")
    suspend fun fetchIncomingRequests(
        @Query("query") offset: Int,
        @Query("limit") limit: Int
    ): Result<List<RequestsInfoResponse>>

    @POST(PATH_REQUESTS)
    suspend fun postRequest(@Body addToContactRequest: AddToContactRequest): Result<CreatedRequestResponse>

    @PATCH("$PATH_REQUESTS/{id}")
    suspend fun patchRequestStatus(@Path("id") id: Long, @Body applyRequestRequest: ApplyRequestRequest): Result<Unit>

    @DELETE("$PATH_REQUESTS/{id}")
    suspend fun deleteRequestById(@Path("id") id: Long): Result<Unit>

    // Users
    @GET(PATH)
    suspend fun fetchUsersInfoByQuery(
        @Query("name") name: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): Result<SearchUsersResponse>

    @GET("$PATH/{id}")
    suspend fun fetchUserInfoById(@Path("id") username: String): Result<UserInfoResponse>

    @GET("$PATH/light")
    suspend fun fetchUsersLightInfoByIds(@Query("ids") ids: List<String>): Result<List<UserLightInfoResponse>>
}

private const val PATH = "/users/api-v2"
private const val PATH_CONTACTS = "$PATH/contacts"
private const val PATH_PROFILE = "$PATH/profile"
private const val PATH_REQUESTS = "$PATH/requests"