package com.point.services.chats.di

import com.point.services.chats.services.ChatsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
internal object ChatsServiceModule {

    @Provides
    fun provideChatsService(retrofit: Retrofit) = retrofit.create<ChatsService>()
}