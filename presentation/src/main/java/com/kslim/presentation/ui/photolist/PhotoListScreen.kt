package com.kslim.presentation.ui.photolist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kslim.presentation.R
import com.kslim.presentation.component.PhotoErrorContent
import com.kslim.presentation.component.PhotoExplorerTopBar
import com.kslim.presentation.theme.PhotoExplorerTheme
import com.kslim.presentation.ui.photolist.component.PhotoGridItem
import com.kslim.presentation.ui.photolist.model.PhotoUiModel


@Composable
fun PhotoListScreen(
    onNavigateToFavorite: () -> Unit,
    onNavigateToDetail: (String) -> Unit,
    viewModel: PhotoListViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                PhotoListSideEffect.NavigateToFavorite -> onNavigateToFavorite()
                is PhotoListSideEffect.NavigateToDetail -> onNavigateToDetail(effect.photoId)
                is PhotoListSideEffect.ShowSnackBar -> snackBarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        topBar = {
            PhotoExplorerTopBar(
                title = "Photo Explorer",
                actions = {
                    IconButton(onClick = onNavigateToFavorite) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorites"
                        )
                    }
                }
            )
        }
    ) { padding ->
        PhotoListContent(
            modifier = Modifier.padding(padding),
            state = state,
            onPhotoClick = { photoId ->
                onNavigateToDetail(photoId)
            },
            onFavoriteClick = { photo ->
                viewModel.onIntent(PhotoListIntent.ToggleFavorite(photo))
            },
            onRetryClick = {
                viewModel.onIntent(PhotoListIntent.LoadPhotos)
            },
            onLoadMore = {
                viewModel.onIntent(PhotoListIntent.LoadMore)
            }
        )
    }
}

@Composable
fun PhotoListContent(
    modifier: Modifier = Modifier,
    state: PhotoListState,
    onPhotoClick: (String) -> Unit,
    onFavoriteClick: (PhotoUiModel) -> Unit,
    onRetryClick: () -> Unit,
    onLoadMore: () -> Unit
) {
    when {
        // 최초 로딩
        state.isLoading && state.photos.isEmpty() -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        // 초기 로드 에러 ( 데이터 없을 때 또는 네트워크 연결이 안되었을 경우
        state.errorMessage != null && state.photos.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                PhotoErrorContent(
                    message = state.errorMessage,
                    onRetryClick = onRetryClick
                )
            }
        }
        // 데이터가 없는 경우
        !state.isLoading && state.photos.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                PhotoErrorContent(
                    message = stringResource(R.string.error_photo_list_empty),
                    onRetryClick = onRetryClick
                )
            }
        }
        else -> {
            val gridState = rememberLazyGridState()
            val shouldLoadMore by remember {
                derivedStateOf {
                    val layoutInfo = gridState.layoutInfo
                    val totalItems = layoutInfo.totalItemsCount
                    val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1

                    // 마지먹 도달 전 미리 데이터 요청
                    totalItems > 0 && lastVisibleItemIndex >= totalItems - 4
                }
            }

            LaunchedEffect(shouldLoadMore) {
                // 중복 호출 방지
                if (shouldLoadMore && !state.isLoadingMore && !state.isLoading) {
                    onLoadMore()
                }
            }

            LazyVerticalGrid(
                modifier = modifier.fillMaxSize(),
                state = gridState,
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

                // 무한 스크롤, 로딩 바 노출
                if (state.isLoadingMore) {
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhotoListContentPreview() {
    PhotoExplorerTheme {
        PhotoListContent(
            state = PhotoListState(photos = emptyList(), errorMessage = "data error"),
            onPhotoClick = {},
            onFavoriteClick = {},
            onRetryClick = {},
            onLoadMore = {})
    }
}