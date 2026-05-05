package com.kslim.presentation.ui.model

import androidx.compose.runtime.Immutable
import com.kslim.domain.model.FavoritePhoto
import com.kslim.domain.model.Photo
import com.kslim.domain.model.PhotoDetail


sealed class PhotoUiModel(
    open val id: String,
    open val imageUrl: String,
    open val userProfile: PhotoUserProfile,
    open val isFavorite: Boolean
) {
    @Immutable
    data class PhotoList(
        override val id: String,
        override val imageUrl: String,                              // Urls 중 regular
        override val userProfile: PhotoUserProfile,
        override val isFavorite: Boolean = false
    ) : PhotoUiModel(id, imageUrl, userProfile, isFavorite)


    @Immutable
    data class PhotoFavorite(
        override val id: String,
        override val imageUrl: String,                // Urls 중 regular
        override val userProfile: PhotoUserProfile,
        override val isFavorite: Boolean = false,
        val localPath: String? = null
    ) : PhotoUiModel(id, imageUrl, userProfile, isFavorite)

    @Immutable
    data class PhotoDetail(
        override val id: String,
        override val imageUrl: String,                // full image url
        override val userProfile: PhotoUserProfile,
        override val isFavorite: Boolean = false,
        val description: String,
        val likes: Int,
        val downloads: Int,
        val tags: List<String>
    ) : PhotoUiModel(id, imageUrl, userProfile, isFavorite)
}

// Photo Main 화면 UiModel
fun Photo.toPhotoListUiModel() = PhotoUiModel.PhotoList(
    id = this.id,
    imageUrl = this.urls?.regular ?: "",
    userProfile = PhotoUserProfile(
        name = this.user?.username ?: "",
        profileImageUrl = this.user?.profileImage?.small ?: ""
    ),
    isFavorite = this.isFavorite
)

// Photo Favorite 화면 UiModel
fun FavoritePhoto.toPhotoFavoriteUiModel() = PhotoUiModel.PhotoFavorite(
    id = this.id,
    imageUrl = this.imageUrl,
    userProfile = PhotoUserProfile(
        name = this.userName,
        profileImageUrl = this.userProfileImageUrl
    ),
    isFavorite = this.isFavorite,
    localPath = this.localPath
)

// Photo Detail 화면 UiModel
fun PhotoDetail.toPhotoDetailUiModel() = PhotoUiModel.PhotoDetail(
    id = this.id,
    imageUrl = this.urls?.full ?: "",
    userProfile = PhotoUserProfile(
        name = this.user?.username ?: "",
        profileImageUrl = this.user?.profileImage?.small ?: "",
        instagramUsername = this.user?.instagramUsername?.ifEmpty { null },
        twitterUsername = this.user?.twitterUsername?.ifEmpty { null }
    ),
    isFavorite = this.isFavorite,
    likes = this.likes,
    downloads = this.downloads,
    description = this.description,
    tags = this.tags.map { it.title }
)

fun PhotoUiModel.toFavoritePhoto(): FavoritePhoto {
    return FavoritePhoto(
        id = this.id,
        imageUrl = this.imageUrl,
        userName = this.userProfile.name,
        userProfileImageUrl = this.userProfile.profileImageUrl,
        isFavorite = this.isFavorite,
    )
}