package com.kslim.data.repository

import com.kslim.data.network.result.ApiResult
import com.kslim.data.network.result.toDataError
import com.kslim.data.remote.datasource.PhotoDataSource
import com.kslim.data.remote.mapper.toDomain
import com.kslim.domain.model.Photo
import com.kslim.domain.repository.PhotoRepository
import com.kslim.domain.result.DataResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoDataSource: PhotoDataSource
) : PhotoRepository {
    override suspend fun getPhotos(page: Int, perPage: Int): DataResult<List<Photo>> {
        return when (val result = photoDataSource.getPhotos(page = page, perPage = perPage)) {
            is ApiResult.Failure -> DataResult.Failure(result.exception.toDataError())
            is ApiResult.Success -> {
                DataResult.Success(result.data.map { it.toDomain() })
            }
        }
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