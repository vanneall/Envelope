package com.point.auth.di

import com.point.auth.common.data.AuthorizeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
class ServiceModule {

    @Provides
    @ViewModelScoped
    fun provideAuthService(retrofit: Retrofit) = retrofit.create<AuthorizeService>()

}