package com.kslim.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kslim.data.local.entity.FavoritePhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePhotoDao {
    @Query("SELECT id FROM favorite_photos WHERE isFavorite = 1")
    fun observeFavoriteIds(): Flow<List<String>>

    @Query("SELECT * FROM favorite_photos WHERE isFavorite = 1")
    fun observeFavoritePhotos(): Flow<List<FavoritePhotoEntity>>

    @Query("SELECT id FROM favorite_photos WHERE isFavorite = 1")
    suspend fun getFavoriteIds(): List<String>

    @Query("SELECT * FROM favorite_photos WHERE id = :photoId")
    suspend fun getFavoritePhoto(photoId: String): FavoritePhotoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: FavoritePhotoEntity)

    // 관심 여부 업데이트
    @Query("UPDATE favorite_photos SET isFavorite = :isFavorite WHERE id = :photoId")
    suspend fun updateFavorite(photoId: String, isFavorite: Boolean)

    // 로컬 다운로드 경로 업데이트
    @Query("UPDATE favorite_photos SET localPath = :path, isFavorite =:isFavorite  WHERE id = :photoId")
    suspend fun updateLocalPath(photoId: String, path: String, isFavorite: Boolean)
}