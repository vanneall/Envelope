package com.point.services.media.di

import android.content.Context
import com.point.services.media.repository.MediaRepositoryImpl
import com.point.services.media.services.MediaService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
internal object MediaServiceRepository {

    @Provides
    fun provideMediaRepositoryImpl(@ApplicationContext context: Context, mediaService: MediaService) =
        MediaRepositoryImpl(context = context, mediaService = mediaService)
}