package com.kslim.presentation.ui.photolist.model

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.core.net.toUri
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
) : PhotoUiModel


// Photo Main 화면 UiModel
fun Photo.toPhotoListUiModel(): PhotoListUiModel {

    // 이미지 리사이징
    fun getImageUrlToMakeQueryParameter(imagUrl: String): String {
        if (imagUrl.isEmpty()) return imagUrl

        return runCatching {
            val builder = imagUrl.toUri().buildUpon()

            builder.appendQueryParameter("fit", "crop")
            builder.appendQueryParameter("w", "480")
            builder.appendQueryParameter("h", "480")
            builder.toString()
        }.getOrElse {
            Log.e("kslim", "ImageUrlToMakeQueryParameter exception ${it.message}")
            ""
        }
    }
    return PhotoListUiModel(
        id = this.id,
        imageUrl = getImageUrlToMakeQueryParameter(this.urls?.small ?: ""),
        userProfile = PhotoUserProfile(
            name = this.user?.username ?: "",
            profileImageUrl = this.user?.profileImage?.small ?: ""
        ),
        isFavorite = this.isFavorite
    )
}


fun PhotoListUiModel.toFavoritePhoto(): FavoritePhoto {
    return FavoritePhoto(
        id = this.id,
        imageUrl = this.imageUrl,
        userName = this.userProfile.name,
        userProfileImageUrl = this.userProfile.profileImageUrl,
        isFavorite = this.isFavorite,
    )
}