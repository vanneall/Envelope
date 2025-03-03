package com.point.auth.authorization.di

import com.point.auth.authorization.data.AuthorizationRepository
import com.point.auth.authorization.domain.AuthorizeUseCase
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
        userStorage: UserStorage
    ) = AuthorizeUseCase(
        authorizationRepository = authorizationRepository,
        userStorage = userStorage,
    )

}