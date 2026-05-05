package com.kslim.data.local.datasource

import com.kslim.data.local.entity.FavoritePhotoEntity
import kotlinx.coroutines.flow.Flow

interface PhotoLocalDataSource {
    fun observeFavoriteIds(): Flow<List<String>>

    fun observeFavoritePhotos(): Flow<List<FavoritePhotoEntity>>

    suspend fun getFavoriteIds(): List<String>

    suspend fun isFavorite(photoId: String): Boolean

    suspend fun insertFavorite(photo: FavoritePhotoEntity)

    suspend fun deleteFavorite(photoId: String)
}