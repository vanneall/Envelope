package ru.point.user.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.point.user.repository.UserRepository
import ru.point.user.repository.UserRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}
