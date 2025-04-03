package com.point.auth.di

import com.point.auth.common.data.AuthorizationRepository
import com.point.auth.common.data.AuthorizationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryBindsModule {

    @Binds
    @ViewModelScoped
    abstract fun binAuthRepository(authorizationRepositoryImpl: AuthorizationRepositoryImpl): AuthorizationRepository

}