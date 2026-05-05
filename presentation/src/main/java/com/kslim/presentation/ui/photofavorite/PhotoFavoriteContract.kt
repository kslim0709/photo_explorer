package com.kslim.presentation.ui.photofavorite

import com.kslim.presentation.ui.model.PhotoUiModel


data class PhotoFavoriteState(
    val photos: List<PhotoUiModel.PhotoFavorite> = emptyList(),
    val isLoading: Boolean = false
)

sealed interface PhotoFavoriteIntent {
    data object ClickBack : PhotoFavoriteIntent
    data class ClickPhoto(val photoId: String) : PhotoFavoriteIntent
    data class ToggleFavorite(val photo: PhotoUiModel.PhotoFavorite) : PhotoFavoriteIntent
}


sealed interface PhotoFavoriteSideEffect {
    data object NavigateBack : PhotoFavoriteSideEffect
    data class NavigateToDetail(val photoId: String) : PhotoFavoriteSideEffect
    data class ShowSnackBar(val message: String) : PhotoFavoriteSideEffect
}