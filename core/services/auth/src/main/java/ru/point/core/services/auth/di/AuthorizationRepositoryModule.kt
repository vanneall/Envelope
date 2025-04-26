package ru.point.core.services.auth.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.point.core.services.auth.repository.AuthorizationRepositoryImpl
import ru.point.core.services.auth.services.AuthorizeService

@Module
@InstallIn(ViewModelComponent::class)
internal object AuthorizationRepositoryModule {

    @Provides
    fun provideAuthorizationRepositoryImpl(@ApplicationContext context: Context, authorizeService: AuthorizeService) =
        AuthorizationRepositoryImpl(context = context, authorizeService = authorizeService)
}