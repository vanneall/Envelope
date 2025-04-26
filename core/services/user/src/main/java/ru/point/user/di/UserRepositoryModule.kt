package ru.point.user.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.point.user.repository.UserRepositoryImpl
import ru.point.user.services.UserService

@Module
@InstallIn(ViewModelComponent::class)
internal object UserRepositoryModule {

    @Provides
    fun provideUserRepositoryImpl(userService: UserService) = UserRepositoryImpl(userService = userService)
}
