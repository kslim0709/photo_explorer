package com.kslim.presentation.ui.photofavorite.model

import androidx.compose.runtime.Immutable
import com.kslim.domain.model.FavoritePhoto
import com.kslim.presentation.ui.model.PhotoUiModel
import com.kslim.presentation.ui.model.PhotoUserProfile

@Immutable
data class PhotoFavoriteUiModel(
    override val id: String,
    override val imageUrl: String,                // Urls 중 small
    override val userProfile: PhotoUserProfile,
    override val isFavorite: Boolean = false,
    val localPath: String? = null
) : PhotoUiModel


// Photo Favorite 화면 UiModel
fun FavoritePhoto.toPhotoFavoriteUiModel() = PhotoFavoriteUiModel(
    id = this.id,
    imageUrl = this.localPath ?: this.imageUrl,
    userProfile = PhotoUserProfile(
        name = this.userName,
        profileImageUrl = this.userProfileImageUrl
    ),
    isFavorite = this.isFavorite,
    localPath = this.localPath
)