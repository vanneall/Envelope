package ru.point.user.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.point.user.repository.UserRepositoryImpl
import ru.point.user.services.UserService

@Module
@InstallIn(ViewModelComponent::class)
internal object UserRepositoryModule {

    @Provides
    fun provideUserRepositoryImpl(@ApplicationContext context: Context, userService: UserService) =
        UserRepositoryImpl(context = context, userService = userService)
}
