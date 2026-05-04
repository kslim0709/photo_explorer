package com.kslim.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class CustomJson

    @Provides
    @Singleton
    @CustomJson
    fun provideCustomJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
            isLenient = true
            coerceInputValues = true
        }
    }
}