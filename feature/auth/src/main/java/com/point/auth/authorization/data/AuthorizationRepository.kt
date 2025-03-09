package com.point.auth.authorization.data

import com.point.auth.authorization.token.Token

interface AuthorizationRepository {

    suspend fun tryToAuthorize(authRequest: AuthRequest): Result<Token>

    suspend fun registration(registrationRequest: UserRegistrationRequest): Result<Token>

}