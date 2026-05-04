package com.kslim.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kslim.data.network.Network
import com.kslim.data.network.NetworkConstants
import com.kslim.data.network.NetworkImpl
import com.kslim.data.network.create
import com.kslim.data.network.interceptor.AuthInterceptor
import com.kslim.data.remote.api.UnsplashApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun providesOkhttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(authInterceptor)
        }.build()
    }


    @Provides
    @Singleton
    fun providesRetrofitBuilder(
        @CommonModule.CustomJson json: Json,
        okHttpClient: OkHttpClient
    ): Retrofit.Builder =
        Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)

    @Provides
    fun providesNetwork(
        retrofitBuilder: Retrofit.Builder
    ): Network = NetworkImpl(retrofitBuilder)

    @Singleton
    @Provides
    fun provideUnsplashApi(network: Network): UnsplashApi =
        network.create<UnsplashApi>(baseUrl = NetworkConstants.BASE_URL)
}