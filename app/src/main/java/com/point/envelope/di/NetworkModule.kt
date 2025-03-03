package com.point.envelope.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun provideRetrofit() = Retrofit.Builder()
        .baseUrl("http://192.168.0.192:8080")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .build()


    @Provides
    fun provideContext(@ApplicationContext context: Context) = context
}