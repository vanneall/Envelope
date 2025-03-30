package com.example.settings.di

import com.example.settings.data.ContactsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
class SettingsModule {

    @Provides
    @ViewModelScoped
    fun provideContactService(retrofit: Retrofit) = retrofit.create<ContactsService>()

}