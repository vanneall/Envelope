package com.point.chats.di

import com.point.chats.create.repository.ContactsService
import com.point.chats.dialog.data.MediaContentService
import com.point.chats.main.data.reporitory.ChatsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
class ApiModule {

    @Provides
    @ViewModelScoped
    fun provideChatsService(retrofit: Retrofit) = retrofit.create<ChatsService>()

    @Provides
    @ViewModelScoped
    fun provideContactsService(retrofit: Retrofit) = retrofit.create<ContactsService>()

    @Provides
    @ViewModelScoped
    fun provideMediaService(retrofit: Retrofit) = retrofit.create<MediaContentService>()

}