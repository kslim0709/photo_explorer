package com.kslim.data.local.mapper

import com.kslim.data.local.entity.FavoritePhotoEntity
import com.kslim.domain.model.FavoritePhoto


fun FavoritePhoto.toEntity(): FavoritePhotoEntity {
    return FavoritePhotoEntity(
        id = id,
        imageUrl = imageUrl,
        userName = userName,
        userProfileImageUrl = userProfileImageUrl
    )
}

fun FavoritePhotoEntity.toDomain(): FavoritePhoto {
    return FavoritePhoto(
        id = id,
        imageUrl = imageUrl,
        userName = userName,
        userProfileImageUrl = userProfileImageUrl
    )
}