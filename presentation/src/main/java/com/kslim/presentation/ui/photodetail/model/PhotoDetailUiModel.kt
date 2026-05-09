package com.kslim.presentation.ui.photodetail.model

import androidx.compose.runtime.Immutable
import com.kslim.domain.model.PhotoDetail
import com.kslim.presentation.ui.model.PhotoUiModel
import com.kslim.presentation.ui.model.PhotoUserProfile

@Immutable
data class PhotoDetailUiModel(
    override val id: String,
    override val imageUrl: String,                // full image url
    override val userProfile: PhotoUserProfile,
    override val isFavorite: Boolean = false,
    val description: String,
    val likes: Int,
    val downloads: Int,
    val tags: List<String>
) : PhotoUiModel

// Photo Detail 화면 UiModel
fun PhotoDetail.toPhotoDetailUiModel() = PhotoDetailUiModel(
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
    tags = this.tags.map { it.title },
)