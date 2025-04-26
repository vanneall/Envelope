package ru.point.core.services.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.create
import ru.point.core.services.auth.services.AuthorizeService

@Module
@InstallIn(ViewModelComponent::class)
internal object AuthorizationServiceModule {

    @Provides
    fun provideAuthorizationService(retrofit: Retrofit) = retrofit.create<AuthorizeService>()
}