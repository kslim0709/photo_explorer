package com.kslim.data.di

import android.content.Context
import androidx.room.Room
import com.kslim.data.database.PhotoDatabase
import com.kslim.data.local.dao.FavoritePhotoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): PhotoDatabase {
        return Room.databaseBuilder(
            context, PhotoDatabase::class.java, "PhotoDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun providesFavoritePhotoDao(database: PhotoDatabase): FavoritePhotoDao {
        return database.favoritePhotoDao()
    }
}