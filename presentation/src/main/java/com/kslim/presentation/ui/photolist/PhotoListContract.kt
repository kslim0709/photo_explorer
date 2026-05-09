package com.kslim.presentation.ui.photolist

import androidx.compose.runtime.Immutable
import com.kslim.presentation.ui.photolist.model.PhotoListUiModel


@Immutable
data class PhotoListState(
    val photos: List<PhotoListUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val isDownloading: Boolean = false,
    val page: Int = 1,
    val errorMessage: String? = null
)

sealed interface PhotoListIntent {
    data object LoadPhotos : PhotoListIntent
    data object LoadMore : PhotoListIntent
    data class ToggleFavorite(val photo: PhotoListUiModel) : PhotoListIntent
}


sealed interface PhotoListSideEffect {
    data object NavigateToFavorite : PhotoListSideEffect
    data class NavigateToDetail(val photoId: String) : PhotoListSideEffect

    data class ShowSnackBar(val message: String) : PhotoListSideEffect
}