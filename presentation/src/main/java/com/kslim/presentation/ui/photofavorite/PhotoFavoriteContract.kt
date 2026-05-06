package com.kslim.presentation.ui.photofavorite

import androidx.compose.runtime.Immutable
import com.kslim.presentation.ui.photofavorite.model.PhotoFavoriteUiModel

@Immutable
data class PhotoFavoriteState(
    val photos: List<PhotoFavoriteUiModel> = emptyList(),
    val isLoading: Boolean = false
)

sealed interface PhotoFavoriteIntent {
    data object ClickBack : PhotoFavoriteIntent
    data class ClickPhoto(val photoId: String) : PhotoFavoriteIntent
    data class ToggleFavorite(val photo: PhotoFavoriteUiModel) : PhotoFavoriteIntent
}


sealed interface PhotoFavoriteSideEffect {
    data object NavigateBack : PhotoFavoriteSideEffect
    data class NavigateToDetail(val photoId: String) : PhotoFavoriteSideEffect
    data class ShowSnackBar(val message: String) : PhotoFavoriteSideEffect
}