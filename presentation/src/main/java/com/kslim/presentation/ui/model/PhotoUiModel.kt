package com.kslim.presentation.ui.model

import androidx.compose.runtime.Immutable
import com.kslim.domain.model.FavoritePhoto
import com.kslim.domain.model.Photo


sealed class PhotoUiModel(
    open val id: String,
    open val imageUrl: String,                // Urls 중 regular
    open val userName: String,                // User user name
    open val userProfileImageUrl: String,     // User profileImage > small url
    open val isFavorite: Boolean
) {
    @Immutable
    data class PhotoList(
        override val id: String,
        override val imageUrl: String,                // Urls 중 regular
        override val userName: String,                // User user name
        override val userProfileImageUrl: String,     // User profileImage > small url
        override val isFavorite: Boolean = false
    ) : PhotoUiModel(id, imageUrl, userName, userProfileImageUrl, isFavorite)


    @Immutable
    data class PhotoFavorite(
        override val id: String,
        override val imageUrl: String,                // Urls 중 regular
        override val userName: String,                // User user name
        override val userProfileImageUrl: String,     // User profileImage > small url
        override val isFavorite: Boolean = false,
        val localPath: String? = null
    ) : PhotoUiModel(id, imageUrl, userName, userProfileImageUrl, isFavorite)
}

// Photo Main 화면 UiModel
fun Photo.toPhotoListUiModel() = PhotoUiModel.PhotoList(
    id = this.id,
    imageUrl = this.urls?.regular ?: "",
    userName = this.user?.username ?: "",
    userProfileImageUrl = this.user?.profileImage?.small ?: "",
    isFavorite = this.isFavorite
)

// Photo Favorite 화면 UiModel
fun FavoritePhoto.toPhotoFavoriteUiModel() = PhotoUiModel.PhotoFavorite(
    id = this.id,
    imageUrl = this.imageUrl,
    userName = this.userName,
    userProfileImageUrl = this.userProfileImageUrl,
    isFavorite = this.isFavorite,
    localPath = this.localPath
)

fun PhotoUiModel.toFavoritePhoto(): FavoritePhoto {
    return FavoritePhoto(
        id = this.id,
        imageUrl = this.imageUrl,
        userName = this.userName,
        userProfileImageUrl = this.userProfileImageUrl,
        isFavorite = this.isFavorite,
    )
}