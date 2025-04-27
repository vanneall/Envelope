package com.point.services.media.di

import com.point.services.media.services.MediaService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
internal object MediaServiceModule {

    @Provides
    fun provideMediaService(retrofit: Retrofit) = retrofit.create<MediaService>()
}