package com.kslim.presentation.ui.photodetail

import androidx.compose.runtime.Immutable
import com.kslim.presentation.ui.photodetail.model.PhotoDetailUiModel


@Immutable
data class PhotoDetailState(
    val photo: PhotoDetailUiModel? = null,
    val isLoading: Boolean = false,
    val isDownloading: Boolean = false,
    val errorMessage: String? = null
)

sealed interface PhotoDetailIntent {
    data object ClickBack : PhotoDetailIntent
    data object DownloadPhoto : PhotoDetailIntent
    data object PermissionDenied : PhotoDetailIntent

    data class ToggleFavorite(val photo: PhotoDetailUiModel) : PhotoDetailIntent
}

sealed interface PhotoDetailSideEffect {
    data object NavigateBack : PhotoDetailSideEffect
    data class ShowSnackBar(val message: String) : PhotoDetailSideEffect
}