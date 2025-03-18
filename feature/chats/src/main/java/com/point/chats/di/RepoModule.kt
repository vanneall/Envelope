package com.point.chats.di

import com.example.utils.Fake
import com.example.utils.Real
import com.point.chats.main.data.reporitory.ChatRepository
import com.point.chats.main.data.reporitory.ChatRepositoryFake
import com.point.chats.main.data.reporitory.ChatRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoModule {
    
    @Binds
    @Real
    abstract fun bindsChatRepositoryImpl(impl: ChatRepositoryImpl): ChatRepository
    
    @Binds
    @Fake
    abstract fun bindsChatRepositoryFake(fake: ChatRepositoryFake): ChatRepository
    
}