package com.point.user.di

import android.content.Context
import com.point.user.storage.UserStorage
import com.point.user.storage.UserStorageImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UserModule {

    @Provides
    fun provideUserStorage(context: Context): UserStorage =
        UserStorageImpl(context.getSharedPreferences(SHARED_PREFS_USER, Context.MODE_PRIVATE))


    companion object {
        private const val SHARED_PREFS_USER = "user_prefs"
    }
}