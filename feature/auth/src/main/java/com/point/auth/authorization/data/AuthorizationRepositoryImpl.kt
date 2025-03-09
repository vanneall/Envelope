package com.point.auth.authorization.data

import com.point.auth.authorization.token.Token

class AuthorizationRepositoryImpl(private val authorizeService: AuthorizeService) :
    AuthorizationRepository {

    override suspend fun tryToAuthorize(authRequest: AuthRequest): Result<Token> =
        authorizeService.authorize(authRequest = authRequest)

    override suspend fun registration(registrationRequest: UserRegistrationRequest): Result<Token> =
        authorizeService.registration(registrationRequest = registrationRequest)

}