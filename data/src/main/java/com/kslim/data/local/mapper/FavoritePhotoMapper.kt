package com.kslim.data.local.mapper

import com.kslim.data.local.entity.FavoritePhotoEntity
import com.kslim.domain.model.FavoritePhoto


fun FavoritePhoto.toEntity(): FavoritePhotoEntity {
    return FavoritePhotoEntity(
        id = this.id,
        imageUrl = this.imageUrl,
        userName = this.userName,
        userProfileImageUrl = this.userProfileImageUrl,
        isFavorite = true,
        localPath = this.localPath
    )
}

fun FavoritePhotoEntity.toDomain(): FavoritePhoto {
    return FavoritePhoto(
        id = this.id,
        imageUrl = this.imageUrl,
        userName = this.userName,
        userProfileImageUrl = this.userProfileImageUrl,
        isFavorite = this.isFavorite,
        localPath = this.localPath
    )
}