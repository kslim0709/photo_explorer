package com.kslim.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_photos")
data class FavoritePhotoEntity(
    @PrimaryKey val id: String,
    val imageUrl: String,
    val userName: String,
    val userProfileImageUrl: String,

    val localPath: String? = null,          // 로컬 다운로드된 이미지 경로
    val isFavorite: Boolean = false         // 관심 여부
)