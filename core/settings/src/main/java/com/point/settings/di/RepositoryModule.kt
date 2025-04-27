package com.point.settings.di

import com.point.settings.repository.AppSettingsRepository
import com.point.settings.repository.AppSettingsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindSettingsRepository(impl: AppSettingsRepositoryImpl): AppSettingsRepository
}