package com.kslim.domain.repository

import com.kslim.domain.model.FavoritePhoto
import com.kslim.domain.model.Photo
import com.kslim.domain.result.DataResult
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    suspend fun getPhotos(page: Int, perPage: Int): DataResult<List<Photo>>
    suspend fun getPhotoDetail(photoId: String): Photo
    suspend fun toggleFavorite(photo: FavoritePhoto): DataResult<Unit>
    fun observeFavoritePhotos(): Flow<List<FavoritePhoto>>
    fun observeFavoriteIds(): Flow<List<String>>
}