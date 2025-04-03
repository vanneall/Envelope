package com.point.auth.di

import com.point.auth.common.data.AuthorizationRepositoryImpl
import com.point.auth.common.data.AuthorizeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideAuthorizationRepository(authorizeService: AuthorizeService) =
        AuthorizationRepositoryImpl(authorizeService = authorizeService)

}