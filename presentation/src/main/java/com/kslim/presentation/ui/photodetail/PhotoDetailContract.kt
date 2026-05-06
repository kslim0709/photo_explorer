package com.kslim.presentation.ui.photodetail

import com.kslim.presentation.ui.model.PhotoUiModel

data class PhotoDetailState(
    val photo: PhotoUiModel.PhotoDetail? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed interface PhotoDetailIntent {
    data object ClickBack : PhotoDetailIntent
    data object DownloadPhoto : PhotoDetailIntent
    data object PermissionDenied : PhotoDetailIntent

    data class ToggleFavorite(val photo: PhotoUiModel.PhotoDetail) : PhotoDetailIntent
}

sealed interface PhotoDetailSideEffect {
    data object NavigateBack : PhotoDetailSideEffect
    data class ShowSnackBar(val message: String) : PhotoDetailSideEffect
}