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

    override suspend fun isFavorite(photoId: String): Boolean {
        return favoritePhotoDao.isFavorite(photoId)
    }

    override suspend fun insertFavorite(photo: FavoritePhotoEntity) {
        favoritePhotoDao.insert(photo)
    }

    override suspend fun deleteFavorite(photoId: String) {
        favoritePhotoDao.delete(photoId)
    }
}