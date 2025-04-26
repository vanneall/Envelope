package ru.point.user.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.create
import ru.point.user.services.UserService

@Module
@InstallIn(ViewModelComponent::class)
internal class UserServiceModule {

    @Provides
    fun provideUserService(retrofit: Retrofit): UserService = retrofit.create<UserService>()
}