package com.kslim.data.local.datasource

import com.kslim.data.local.entity.FavoritePhotoEntity
import kotlinx.coroutines.flow.Flow

interface PhotoLocalDataSource {
    fun observeFavoriteIds(): Flow<List<String>>

    fun observeFavoritePhotos(): Flow<List<FavoritePhotoEntity>>

    suspend fun getFavoriteIds(): List<String>
    suspend fun getFavoritePhoto(photoId: String): FavoritePhotoEntity?

    suspend fun insertFavorite(photo: FavoritePhotoEntity)

    suspend fun updateFavorite(photoId: String, isFavorite: Boolean)
    suspend fun updateLocalPath(photoId: String, path: String, isFavorite: Boolean)

    // 로컬에 사진 저장
    suspend fun savePhoto(photoName: String, bytes: ByteArray): String?
}