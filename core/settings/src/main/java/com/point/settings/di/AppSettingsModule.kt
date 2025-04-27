package com.point.settings.di

import android.content.Context
import android.content.SharedPreferences
import com.point.settings.repository.AppSettingsRepositoryImpl
import com.point.settings.repository.AppSettingsRepositoryImpl.Companion.SHARED_PREFS_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object AppSettingsModule {

    @Provides
    fun provideAppSettingsImpl(sharedPreferences: SharedPreferences) =
        AppSettingsRepositoryImpl(sharedPrefs = sharedPreferences)

    @Provides
    fun provideAppSettingsSharedPref(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
}