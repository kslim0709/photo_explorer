package com.kslim.presentation.ui.photolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kslim.domain.result.DataResult
import com.kslim.domain.usecase.GetPhotosUseCase
import com.kslim.presentation.ui.model.toUiMessage
import com.kslim.presentation.ui.photolist.model.toPhotoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase
) : ViewModel() {

    // UiState
    private val _state = MutableStateFlow(PhotoListState())
    val state = _state.asStateFlow()

    // UiSideEffect ( Dialog, SnapBar )
    private val _sideEffect = Channel<PhotoListSideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    private val currentState: PhotoListState
        get() = _state.value

    init {
        onIntent(PhotoListIntent.LoadPhotos)
    }

    fun onIntent(intent: PhotoListIntent) {
        when (intent) {
            PhotoListIntent.LoadPhotos -> loadPhotos()
            PhotoListIntent.LoadMore -> loadMore()
            is PhotoListIntent.ToggleFavorite -> TODO()
        }
    }

    fun loadPhotos() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    page = 1,
                    isLoading = true,
                    isLoadingMore = false,
                    errorMessage = null
                )
            }

            fetchPhotos(page = 1, isLoadMore = false)
        }
    }

    private fun loadMore() {
        val state = currentState

        if (state.isLoading || state.isLoadingMore ) return

        viewModelScope.launch {
            val nextPage = state.page + 1

            _state.update {
                it.copy(isLoadingMore = true)
            }

            fetchPhotos(page = nextPage, isLoadMore = true)
        }
    }


    private suspend fun fetchPhotos(page: Int, isLoadMore: Boolean) {
        when (val result = getPhotosUseCase.execute(page = page)) {
            is DataResult.Success -> {
                val newPhotos = result.data.map { model -> model.toPhotoUiModel() }

                _state.update {
                    it.copy(
                        photos = if (isLoadMore) {
                            it.photos + newPhotos
                        } else {
                            newPhotos
                        },
                        page = page,
                        isLoading = false,
                        isLoadingMore = false,
                        errorMessage = null
                    )
                }
            }

            is DataResult.Failure -> {
                val message = result.error.toUiMessage()

                _state.update {
                    it.copy(
                        isLoading = false,
                        isLoadingMore = false,
                        errorMessage = if (isLoadMore) {
                            it.errorMessage
                        } else {
                            message
                        }
                    )
                }
                sendEffect(PhotoListSideEffect.ShowSnackBar(result.error.toUiMessage()))
            }
        }
    }



    private fun sendEffect(effect: PhotoListSideEffect) {
        viewModelScope.launch {
            _sideEffect.send(effect)
        }
    }
}