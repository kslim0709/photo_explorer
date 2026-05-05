package com.kslim.presentation.ui.photolist.model

import androidx.compose.runtime.Immutable
import com.kslim.domain.model.FavoritePhoto
import com.kslim.domain.model.Photo

@Immutable
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
    isFavorite = this.isFavorite
)

fun PhotoUiModel.toFavoritePhoto(): FavoritePhoto {
    return FavoritePhoto(
        id = id,
        imageUrl = imageUrl,
        userName = userName,
        userProfileImageUrl = userProfileImageUrl
    )
}