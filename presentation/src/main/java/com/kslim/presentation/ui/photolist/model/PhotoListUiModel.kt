package com.kslim.presentation.ui.photolist.model

import androidx.compose.runtime.Immutable
import com.kslim.domain.model.FavoritePhoto
import com.kslim.domain.model.Photo
import com.kslim.presentation.ui.model.PhotoUiModel
import com.kslim.presentation.ui.model.PhotoUserProfile


@Immutable
data class PhotoListUiModel(
    override val id: String,
    override val imageUrl: String,                              // Urls 중 small
    override val userProfile: PhotoUserProfile,
    override val isFavorite: Boolean = false
): PhotoUiModel


// Photo Main 화면 UiModel
fun Photo.toPhotoListUiModel() = PhotoListUiModel(
    id = this.id,
    imageUrl = this.urls?.small ?: "",
    userProfile = PhotoUserProfile(
        name = this.user?.username ?: "",
        profileImageUrl = this.user?.profileImage?.small ?: ""
    ),
    isFavorite = this.isFavorite
)

fun PhotoListUiModel.toFavoritePhoto(): FavoritePhoto {
    return FavoritePhoto(
        id = this.id,
        imageUrl = this.imageUrl,
        userName = this.userProfile.name,
        userProfileImageUrl = this.userProfile.profileImageUrl,
        isFavorite = this.isFavorite,
    )
}