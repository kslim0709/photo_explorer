package com.kslim.data.local.datasource.impl

import com.kslim.data.local.dao.FavoritePhotoDao
import com.kslim.data.local.datasource.PhotoLocalDataSource
import com.kslim.data.local.entity.FavoritePhotoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotoLocalDataSourceImpl @Inject constructor(
    private val favoritePhotoDao: FavoritePhotoDao
) : PhotoLocalDataSource {
    override fun observeFavoriteIds(): Flow<List<String>> {
        return favoritePhotoDao.observeFavoriteIds()
    }

    override fun observeFavoritePhotos(): Flow<List<FavoritePhotoEntity>> {
        return favoritePhotoDao.observeFavoritePhotos()
    }

    override suspend fun getFavoriteIds(): List<String> {
        return favoritePhotoDao.getFavoriteIds()
    }

    override suspend fun getFavoritePhoto(photoId: String): FavoritePhotoEntity? {
        return favoritePhotoDao.getFavoritePhoto(photoId)
    }

    override suspend fun insertFavorite(photo: FavoritePhotoEntity) {
        favoritePhotoDao.insert(photo)
    }

    override suspend fun updateFavorite(photoId: String, isFavorite: Boolean) {
        favoritePhotoDao.updateFavorite(photoId, isFavorite)
    }

    override suspend fun updateLocalPath(photoId: String, path: String) {
        favoritePhotoDao.updateLocalPath(photoId, path)
    }
}