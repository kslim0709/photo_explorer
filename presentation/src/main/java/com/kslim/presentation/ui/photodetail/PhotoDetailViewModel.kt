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
import com.kslim.presentation.ui.model.PhotoUiModel
import com.kslim.presentation.ui.model.toFavoritePhoto
import com.kslim.presentation.ui.model.toPhotoDetailUiModel
import com.kslim.presentation.ui.model.toUiMessage
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

    private fun toggleFavorite(photo: PhotoUiModel) {
        viewModelScope.launch {
            when (val result = toggleFavoriteUseCase.execute(photo.toFavoritePhoto())) {
                is DataResult.Success -> Unit
                is DataResult.Failure -> {
                    sendSideEffect(PhotoDetailSideEffect.ShowSnackBar(result.error.toUiMessage()))
                }
            }
        }
    }

    private fun observeFavoriteIds() {
        viewModelScope.launch {
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