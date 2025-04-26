package com.point.auth.registration.domain.usecase

import com.point.kutils.extensions.ignoreValue
import com.point.user.storage.UserStorage
import ru.point.core.services.auth.models.SignupData
import ru.point.core.services.auth.repository.AuthorizationRepository

class RegistrationUseCase(
    private val authorizationRepository: AuthorizationRepository,
    private val userStorage: UserStorage,
) {

    suspend operator fun invoke(signupData: SignupData): Result<Unit> =
        authorizationRepository.singUp(signupData = signupData)
            .onSuccess { userStorage.updateToken(it) }
            .ignoreValue()
}