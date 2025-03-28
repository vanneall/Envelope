package com.point.contacts.di

import com.point.contacts.data.ContactsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
class NetworkModule {

    @Provides
    @ViewModelScoped
    fun provideContactsService(retrofit: Retrofit) = retrofit.create<ContactsService>()

}