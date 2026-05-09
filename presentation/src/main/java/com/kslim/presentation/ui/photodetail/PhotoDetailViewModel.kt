package com.kslim.presentation.ui.photodetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.kslim.domain.result.DataResult
import com.kslim.domain.usecase.GetPhotoDetailUseCase
import com.kslim.domain.usecase.ObserveFavoriteIdsUseCase
import com.kslim.domain.usecase.ToggleFavoriteUseCase
import com.kslim.presentation.navigation.PhotoScreenRoute
import com.kslim.presentation.ui.model.toFavoritePhoto
import com.kslim.presentation.ui.model.toUiMessage
import com.kslim.presentation.ui.photodetail.model.PhotoDetailUiModel
import com.kslim.presentation.ui.photodetail.model.toPhotoDetailUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPhotoDetailUseCase: GetPhotoDetailUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val observeFavoriteIdsUseCase: ObserveFavoriteIdsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(PhotoDetailState())
    val state: StateFlow<PhotoDetailState> = _state.asStateFlow()

    private val _sideEffect = Channel<PhotoDetailSideEffect>(Channel.BUFFERED)
    val sideEffect: Flow<PhotoDetailSideEffect> = _sideEffect.receiveAsFlow()

    init {
        runCatching {
            savedStateHandle.toRoute<PhotoScreenRoute.PhotoDetail>()
        }.onFailure {
            _state.update { it.copy(errorMessage = it.errorMessage) }
        }.onSuccess {
            observeFavoriteIds()
            loadDetail(it.photoId)
        }
    }

    fun onIntent(intent: PhotoDetailIntent) {
        when (intent) {
            PhotoDetailIntent.ClickBack -> {
                sendSideEffect(PhotoDetailSideEffect.NavigateBack)
            }
            PhotoDetailIntent.PermissionDenied -> {
                sendSideEffect(PhotoDetailSideEffect.ShowSnackBar("사진 저장 권한이 필요합니다."))
            }
            is PhotoDetailIntent.ToggleFavorite -> {
                toggleFavorite(intent.photo)
            }
        }
    }

    private fun loadDetail(photoId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = getPhotoDetailUseCase.execute(photoId)) {
                is DataResult.Success -> {
                    _state.update {
                        it.copy(
                            photo = result.data.toPhotoDetailUiModel(),
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
                is DataResult.Failure -> {
                    val message = result.error.toUiMessage()

                    _state.update {
                        it.copy(
                            photo = null,
                            isLoading = false,
                            errorMessage = message
                        )
                    }
                    sendSideEffect(PhotoDetailSideEffect.ShowSnackBar(message))
                }
            }
        }
    }

    // 사진 다운로드 및 관심 등록, 해제
    private fun toggleFavorite(photo: PhotoDetailUiModel) {
        _state.update { it.copy(isDownloading = true) }

        viewModelScope.launch(Dispatchers.IO) {
            when (val result = toggleFavoriteUseCase.execute(photo.toFavoritePhoto())) {
                is DataResult.Success -> {
                    _state.update {
                        it.copy(isDownloading = false)
                    }
                    val message = if(result.data) "좋아요!" else "좋아요 취소"
                    sendSideEffect(PhotoDetailSideEffect.ShowSnackBar(message = message))
                }
                is DataResult.Failure -> {
                    _state.update { it.copy(isDownloading = false) }
                    sendSideEffect(PhotoDetailSideEffect.ShowSnackBar(message = result.error.toUiMessage()))
                }
            }
        }
    }

    private fun observeFavoriteIds() {
        viewModelScope.launch(Dispatchers.IO) {
            observeFavoriteIdsUseCase().collectLatest { favoriteIds ->
                _state.update {
                    it.copy(
                        photo = it.photo?.copy(isFavorite = it.photo.id in favoriteIds)
                    )
                }
            }
        }
    }

    private fun sendSideEffect(effect: PhotoDetailSideEffect) {
        viewModelScope.launch {
            _sideEffect.send(effect)
        }
    }

}