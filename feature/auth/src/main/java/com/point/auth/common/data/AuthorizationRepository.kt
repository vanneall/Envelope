package com.point.auth.common.data

import com.point.auth.registration.presenter.host.UserRegistrationData

interface AuthorizationRepository {

    suspend fun tryToAuthorize(authRequest: AuthRequest): Result<TokenPayload>

    suspend fun registration(dto: UserRegistrationData): Result<TokenPayload>

}