package com.point.auth.authorization.di

import com.point.auth.authorization.data.AuthorizationRepository
import com.point.auth.authorization.data.AuthorizationRepositoryImpl
import com.point.auth.authorization.data.AuthorizeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideAuthorizationRepository(authorizeService: AuthorizeService): AuthorizationRepository =
        AuthorizationRepositoryImpl(authorizeService = authorizeService)

    @Provides
    fun provideAuthService(retrofit: Retrofit) = retrofit.create<AuthorizeService>()

}