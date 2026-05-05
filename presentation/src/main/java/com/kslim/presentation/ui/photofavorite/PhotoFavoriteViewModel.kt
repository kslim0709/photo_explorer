package com.kslim.presentation.ui.photofavorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kslim.domain.result.DataResult
import com.kslim.domain.usecase.ObserveFavoritePhotosUseCase
import com.kslim.domain.usecase.ToggleFavoriteUseCase
import com.kslim.presentation.ui.model.PhotoUiModel
import com.kslim.presentation.ui.model.toFavoritePhoto
import com.kslim.presentation.ui.model.toPhotoFavoriteUiModel
import com.kslim.presentation.ui.model.toUiMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PhotoFavoriteViewModel @Inject constructor(
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val observeFavoriteIdsUseCase: ObserveFavoritePhotosUseCase
) : ViewModel() {

    // UiState
    private val _state = MutableStateFlow(PhotoFavoriteState(isLoading = true))
    val state = _state.asStateFlow()

    // UiSideEffect ( Dialog, SnapBar )
    private val _sideEffect = Channel<PhotoFavoriteSideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()


    init {
        observeFavorites()
    }


    fun onIntent(intent: PhotoFavoriteIntent) {
        when (intent) {
            PhotoFavoriteIntent.ClickBack -> {
                sendSideEffect(PhotoFavoriteSideEffect.NavigateBack)
            }

            is PhotoFavoriteIntent.ClickPhoto -> {
                sendSideEffect(PhotoFavoriteSideEffect.NavigateToDetail(intent.photoId))
            }

            is PhotoFavoriteIntent.ToggleFavorite -> {
                toggleFavorite(intent.photo)
            }
        }
    }


    private fun observeFavorites() {
        viewModelScope.launch {
            observeFavoriteIdsUseCase().collectLatest { favorites ->
                _state.update {
                    it.copy(
                        photos = favorites.map { favorite ->
                            favorite.toPhotoFavoriteUiModel()
                        },
                        isLoading = false
                    )
                }
                if(favorites.isEmpty()) {
                    sendSideEffect(PhotoFavoriteSideEffect.ShowSnackBar("관심 목록에 추가된 사진이 없습니다."))
                }
            }
        }
    }

    // Photo 관심 토글
    private fun toggleFavorite(photo: PhotoUiModel) {
        viewModelScope.launch {
            when (val result = toggleFavoriteUseCase.execute(photo.toFavoritePhoto())) {
                is DataResult.Success -> Unit
                is DataResult.Failure -> {
                    sendSideEffect(PhotoFavoriteSideEffect.ShowSnackBar(result.error.toUiMessage()))
                }
            }
        }
    }

    private fun sendSideEffect(effect: PhotoFavoriteSideEffect) {
        viewModelScope.launch {
            _sideEffect.send(effect)
        }
    }
}