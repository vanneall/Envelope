package com.point.envelope.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.point.envelope.network.TokenProviderImpl
import com.point.network.di.AuthInterceptor
import com.point.network.di.TokenProvider
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

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
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl("http://192.168.0.192:8080")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()


    @Provides
    fun provideContext(@ApplicationContext context: Context) = context
}


@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkBindsModule {

    @Binds
    abstract fun bindsTokenProvider(tokenProviderImpl: TokenProviderImpl): TokenProvider

}

@Module
@InstallIn(SingletonComponent::class)
class NetworkInterceptorModule {

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenProvider: TokenProvider) = AuthInterceptor(tokenProvider)

}