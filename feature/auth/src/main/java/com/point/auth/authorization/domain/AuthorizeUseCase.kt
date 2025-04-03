package com.point.auth.authorization.domain

import com.point.auth.common.data.AuthRequest
import com.point.auth.common.data.AuthorizationRepository
import com.point.kutils.extensions.ignoreValue
import com.point.user.storage.UserStorage

class AuthorizeUseCase(
    private val authorizationRepository: AuthorizationRepository,
    private val userStorage: UserStorage,
) {

    suspend operator fun invoke(authRequest: AuthRequest): Result<Unit> =
        authorizationRepository.tryToAuthorize(authRequest = authRequest)
            .onSuccess { userStorage.updateToken(it.token) }
            .ignoreValue()
}