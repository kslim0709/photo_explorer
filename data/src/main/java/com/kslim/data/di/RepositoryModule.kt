package com.kslim.data.di

import com.kslim.data.repository.PhotoRepositoryImpl
import com.kslim.domain.repository.PhotoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPhotoRepository( impl: PhotoRepositoryImpl): PhotoRepository

}
