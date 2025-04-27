package com.point.services.media.di

import com.point.services.media.repository.MediaRepository
import com.point.services.media.repository.MediaRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindChatsRepositoryImpl(impl: MediaRepositoryImpl): MediaRepository
}