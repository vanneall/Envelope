package ru.point.core.services.auth.services

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import ru.point.core.services.auth.requests.SignInRequest
import ru.point.core.services.auth.responses.TokenResponse

internal interface AuthorizeService {

    @POST("$PATH/login")
    suspend fun authorize(@Body signInRequest: SignInRequest): Result<TokenResponse>

    @Multipart
    @POST("$PATH/registration")
    suspend fun registration(
        @Part("user") userJson: RequestBody,
        @Part photo: MultipartBody.Part? = null,
    ): Result<TokenResponse>
}

private const val PATH = "/auth/api-v2"