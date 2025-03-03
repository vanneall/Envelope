package com.point.auth.authorization.data

import com.point.auth.authorization.token.Token
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthorizeService {

    @POST("/auth/api-v1/login")
    suspend fun authorize(@Body authRequest: AuthRequest): Result<Token>

}