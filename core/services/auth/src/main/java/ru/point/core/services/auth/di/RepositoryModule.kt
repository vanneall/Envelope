package ru.point.core.services.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.point.core.services.auth.repository.AuthorizationRepository
import ru.point.core.services.auth.repository.AuthorizationRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindUserRepository(impl: AuthorizationRepositoryImpl): AuthorizationRepository
}