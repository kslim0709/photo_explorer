package com.kslim.domain.repository

import com.kslim.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    suspend fun getPhotos(page: Int): List<Photo>
    suspend fun getPhotoDetail(photoId: String): Photo
    fun observeFavoritePhotos(): Flow<List<Photo>>
    suspend fun toggleFavorite(photo: Photo)
}