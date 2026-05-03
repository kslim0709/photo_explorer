package com.kslim.data.repository

import com.kslim.data.remote.datasource.PhotoDataSource
import com.kslim.data.remote.mapper.toDomain
import com.kslim.domain.model.Photo
import com.kslim.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.collections.map

class PhotoRepositoryImpl @Inject constructor(
    private val photoDataSource: PhotoDataSource
) : PhotoRepository {
    override suspend fun getPhotos(page: Int): List<Photo> {
        return photoDataSource.getPhotos(page = page, perPage = 20).map { it.toDomain() }
    }

    override suspend fun getPhotoDetail(photoId: String): Photo {
        TODO("Not yet implemented")
    }

    override fun observeFavoritePhotos(): Flow<List<Photo>> {
        TODO("Not yet implemented")
    }

    override suspend fun toggleFavorite(photo: Photo) {
        TODO("Not yet implemented")
    }
}