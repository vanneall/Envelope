package com.point.auth.di

import com.point.auth.authorization.domain.AuthorizeUseCase
import com.point.auth.common.data.AuthorizationRepository
import com.point.auth.registration.domain.usecase.RegistrationUseCase
import com.point.user.storage.UserStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun provideAuthorizeUseCase(
        authorizationRepository: AuthorizationRepository,
        userStorage: UserStorage,
    ) = AuthorizeUseCase(
        authorizationRepository = authorizationRepository,
        userStorage = userStorage,
    )

    @Provides
    fun provideRegistrationUseCase(
        authorizationRepository: AuthorizationRepository,
        userStorage: UserStorage,
    ) = RegistrationUseCase(
        authorizationRepository = authorizationRepository,
        userStorage = userStorage,
    )
}