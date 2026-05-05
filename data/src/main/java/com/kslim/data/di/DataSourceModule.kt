package com.kslim.data.di

import com.kslim.data.local.datasource.PhotoLocalDataSource
import com.kslim.data.local.datasource.impl.PhotoLocalDataSourceImpl
import com.kslim.data.remote.datasource.PhotoDataSource
import com.kslim.data.remote.datasource.impl.PhotoDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    // Remote
    @Binds
    @Singleton
    abstract fun bindPhotoDataSource(impl: PhotoDataSourceImpl): PhotoDataSource


    // Local (database)
    @Binds
    @Singleton
    abstract fun bindPhotoLocalDataSource(impl: PhotoLocalDataSourceImpl): PhotoLocalDataSource
}