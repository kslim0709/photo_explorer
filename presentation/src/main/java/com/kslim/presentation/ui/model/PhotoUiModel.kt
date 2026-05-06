package com.kslim.presentation.ui.model

import com.kslim.domain.model.FavoritePhoto

interface PhotoUiModel {
    val id: String
    val imageUrl: String
    val userProfile: PhotoUserProfile
    val isFavorite: Boolean
}


fun PhotoUiModel.toFavoritePhoto(): FavoritePhoto {
    return FavoritePhoto(
        id = this.id,
        imageUrl = this.imageUrl,
        userName = this.userProfile.name,
        userProfileImageUrl = this.userProfile.profileImageUrl,
        isFavorite = this.isFavorite,
    )
}