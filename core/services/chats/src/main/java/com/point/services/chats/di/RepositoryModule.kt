package com.point.services.chats.di

import com.point.services.chats.repository.ChatsRepository
import com.point.services.chats.repository.ChatsRepositoryImpl
import com.point.services.chats.repository.DialogRepository
import com.point.services.chats.repository.DialogRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindChatsRepositoryImpl(impl: ChatsRepositoryImpl): ChatsRepository

    @Binds
    fun bindDialogRepositoryImpl(impl: DialogRepositoryImpl): DialogRepository
}