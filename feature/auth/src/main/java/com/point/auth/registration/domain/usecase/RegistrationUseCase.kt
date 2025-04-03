package com.point.auth.registration.domain.usecase

import com.point.auth.common.data.AuthorizationRepository
import com.point.auth.registration.presenter.host.UserRegistrationData
import com.point.kutils.extensions.ignoreValue
import com.point.user.storage.UserStorage

class RegistrationUseCase(
    private val authorizationRepository: AuthorizationRepository,
    private val userStorage: UserStorage,
) {

    suspend operator fun invoke(registrationRequest: UserRegistrationData): Result<Unit> =
        authorizationRepository.registration(dto = registrationRequest)
            .onSuccess { userStorage.updateToken(it.token) }
            .ignoreValue()
}