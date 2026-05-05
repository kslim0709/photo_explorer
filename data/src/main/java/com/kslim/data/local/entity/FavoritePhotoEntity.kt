package com.kslim.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_photos")
data class FavoritePhotoEntity(
    @PrimaryKey val id: String,
    val imageUrl: String,
    val userName: String,
    val userProfileImageUrl: String
)