package com.kslim.presentation.ui.photofavorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kslim.presentation.R
import com.kslim.presentation.ui.component.PhotoErrorContent
import com.kslim.presentation.ui.component.PhotoExplorerTopBar
import com.kslim.presentation.ui.component.PhotoGridItem
import com.kslim.presentation.ui.model.PhotoUiModel
import kotlinx.coroutines.launch

@Composable
fun PhotoFavoriteScreen(
    onBackClick: () -> Unit,
    onNavigateToDetail: (String) -> Unit,
    viewModel: PhotoFavoriteViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is PhotoFavoriteSideEffect.ShowSnackBar -> {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(effect.message)
                    }
                }
                PhotoFavoriteSideEffect.NavigateBack -> {
                    snackBarHostState.currentSnackbarData?.dismiss()
                    onBackClick()
                }
                is PhotoFavoriteSideEffect.NavigateToDetail -> onNavigateToDetail(effect.photoId)
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        topBar = {
            PhotoExplorerTopBar(
                title = stringResource(R.string.title_favorites),
                showBackButton = true,
                onBackClick = {
                    viewModel.onIntent(PhotoFavoriteIntent.ClickBack)
                }
            )
        }
    ) { padding ->
        PhotoFavoriteContent(
            modifier = Modifier.padding(padding),
            state = state,
            onPhotoClick = { photoId ->
                viewModel.onIntent(PhotoFavoriteIntent.ClickPhoto(photoId))
            },
            onFavoriteClick = { photo ->
                viewModel.onIntent(PhotoFavoriteIntent.ToggleFavorite(photo))
            }
        )
    }
}

@Composable
private fun PhotoFavoriteContent(
    modifier: Modifier = Modifier,
    state: PhotoFavoriteState,
    onPhotoClick: (String) -> Unit,
    onFavoriteClick: (PhotoUiModel.PhotoFavorite) -> Unit
) {
    when {
        // 첫 진입 시, 로딩
        state.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // 관심 목록이 없는 경우
        state.photos.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                PhotoErrorContent(
                    message = stringResource(R.string.info_favorite_photo_list_empty),
                )
            }
        }

        else -> {
            LazyVerticalGrid(
                modifier = modifier.fillMaxSize(),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    count = state.photos.size,
                    key = { index -> "${state.photos[index].id}_${index}" }
                ) { index ->
                    val photo = state.photos[index]

                    PhotoGridItem(
                        photo = photo,
                        onClick = { onPhotoClick(photo.id) },
                        onFavoriteClick = { onFavoriteClick(photo) }
                    )
                }
            }
        }
    }
}