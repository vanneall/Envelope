package com.point.auth.authorization.domain

import com.point.kutils.extensions.ignoreValue
import com.point.user.storage.UserStorage
import ru.point.core.services.auth.models.SigninData
import ru.point.core.services.auth.repository.AuthorizationRepository

class AuthorizeUseCase(
    private val authorizationRepository: AuthorizationRepository,
    private val userStorage: UserStorage,
) {

    suspend operator fun invoke(signinData: SigninData): Result<Unit> =
        authorizationRepository.signIn(signinData = signinData)
            .onSuccess { userStorage.updateToken(it) }
            .ignoreValue()
}