package com.kslim.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kslim.data.local.dao.FavoritePhotoDao
import com.kslim.data.local.entity.FavoritePhotoEntity

@Database(entities = [FavoritePhotoEntity::class], version = 1, exportSchema = false)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun favoritePhotoDao(): FavoritePhotoDao
}