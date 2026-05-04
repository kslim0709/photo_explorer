package com.kslim.presentation.ui.photolist.model

import com.kslim.domain.model.Photo

data class PhotoUiModel(
    val id: String,
    val imageUrl: String,                // Urls 중 regular
    val userName: String,                // User user name
    val userProfileImageUrl: String,     // User profileImage > small url
    val isFavorite: Boolean = false
)

fun Photo.toPhotoUiModel() = PhotoUiModel(
    id = this.id,
    imageUrl = this.urls?.regular ?: "",
    userName = this.user?.username ?: "",
    userProfileImageUrl = this.user?.profileImage?.small ?: "",
)