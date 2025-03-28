package com.point.auth.authorization.data

import com.point.auth.authorization.token.Token
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthorizeService {

    @POST("/auth/api-v2/login")
    suspend fun authorize(@Body authRequest: AuthRequest): Result<Token>

    @Multipart
    @POST("/auth/api-v2/registration")
    suspend fun registration(
        @Part("user") userJson: RequestBody,
        @Part photo: MultipartBody.Part? = null
    ): Result<Token>

}