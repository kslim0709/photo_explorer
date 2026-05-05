package com.kslim.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kslim.data.local.entity.FavoritePhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePhotoDao {

    @Query("SELECT id FROM favorite_photos")
    fun observeFavoriteIds(): Flow<List<String>>

    @Query("SELECT * FROM favorite_photos")
    fun observeFavoritePhotos(): Flow<List<FavoritePhotoEntity>>

    @Query("SELECT id FROM favorite_photos")
    suspend fun getFavoriteIds(): List<String>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_photos WHERE id = :photoId)")
    suspend fun isFavorite(photoId: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: FavoritePhotoEntity)

    @Query("DELETE FROM favorite_photos WHERE id = :photoId")
    suspend fun delete(photoId: String)
}