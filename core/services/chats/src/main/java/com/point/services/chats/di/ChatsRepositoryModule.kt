package com.point.services.chats.di

import com.point.network.di.TokenProvider
import com.point.services.chats.repository.ChatsRepositoryImpl
import com.point.services.chats.repository.DialogRepositoryImpl
import com.point.services.chats.services.ChatsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient

@Module
@InstallIn(ViewModelComponent::class)
internal object ChatsRepositoryModule {

    @Provides
    fun provideChatsRepositoryImpl(chatsService: ChatsService) = ChatsRepositoryImpl(chatsService = chatsService)

    @Provides
    fun provideDialogRepositoryImpl(okHttpClient: OkHttpClient, tokenProvider: TokenProvider) =
        DialogRepositoryImpl(okHttpClient = okHttpClient, tokenProvider = tokenProvider)
}