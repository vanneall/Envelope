package com.point.auth.authorization.data

import com.point.auth.authorization.token.Token
import com.point.auth.registration.presenter.mvi.UserRegistrationData

interface AuthorizationRepository {

    suspend fun tryToAuthorize(authRequest: AuthRequest): Result<Token>

    suspend fun registration(dto: UserRegistrationData): Result<Token>

}