package com.kslim.data.repository

import com.kslim.data.local.datasource.PhotoLocalDataSource
import com.kslim.data.local.mapper.toDomain
import com.kslim.data.local.mapper.toEntity
import com.kslim.data.network.result.ApiResult
import com.kslim.data.network.result.toDataError
import com.kslim.data.remote.datasource.PhotoDataSource
import com.kslim.data.remote.mapper.toDomain
import com.kslim.domain.model.FavoritePhoto
import com.kslim.domain.model.Photo
import com.kslim.domain.repository.PhotoRepository
import com.kslim.domain.result.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoDataSource: PhotoDataSource,
    private val photoLocalDataSource: PhotoLocalDataSource
) : PhotoRepository {
    override suspend fun getPhotos(page: Int, perPage: Int): DataResult<List<Photo>> {
        return when (val result = photoDataSource.getPhotos(page = page, perPage = perPage)) {
            is ApiResult.Failure -> DataResult.Failure(result.exception.toDataError())
            is ApiResult.Success -> {
                // API 응답 값 Photo 중 관심 Photo 존재 여부 체크
                val favoriteIds = photoLocalDataSource.getFavoriteIds()

                DataResult.Success(data = result.data.map { it.toDomain(isFavorite = it.id in favoriteIds) })
            }
        }
    }

    override suspend fun getPhotoDetail(photoId: String): Photo {
        TODO("Not yet implemented")
    }

    // Database 내 관심 목록이 변경 되었을 경우 > 관심 목록 화면
    override fun observeFavoritePhotos(): Flow<List<FavoritePhoto>> {
        return photoLocalDataSource.observeFavoritePhotos()
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }

    // Database 내 관심 목록이 변경 되었을 경우 > 메인 화면
    override fun observeFavoriteIds(): Flow<List<String>> {
        return photoLocalDataSource.observeFavoriteIds()
    }

    override suspend fun toggleFavorite(photo: FavoritePhoto): DataResult<Unit> {
        return runCatching {
            if (photoLocalDataSource.isFavorite(photo.id)) {
                photoLocalDataSource.deleteFavorite(photo.id)
            } else {
                photoLocalDataSource.insertFavorite(photo.toEntity())
            }
            DataResult.Success(Unit)
        }.getOrElse {
            DataResult.Failure(it.toDataError())
        }
    }
}