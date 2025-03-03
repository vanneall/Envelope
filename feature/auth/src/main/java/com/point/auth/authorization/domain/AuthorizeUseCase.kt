package com.point.auth.authorization.domain

import com.point.auth.authorization.data.AuthRequest
import com.point.auth.authorization.data.AuthorizationRepository
import com.point.user.storage.UserStorage
import com.skydoves.retrofit.adapters.result.foldSuspend

class AuthorizeUseCase(
    private val authorizationRepository: AuthorizationRepository,
    private val userStorage: UserStorage
) {

    suspend fun execute(authRequest: AuthRequest): Result<Unit> {
        return authorizationRepository.tryToAuthorize(authRequest = authRequest)
            .foldSuspend(
                onSuccess = { token ->
                    userStorage.token = token.token
                    Result.success(Unit)
                },
                onFailure = {
                    Result.failure(it)
                }
            )
    }
}