package com.kslim.presentation.ui.photodetail

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kslim.presentation.R
import com.kslim.presentation.ui.component.PhotoAsyncImage
import com.kslim.presentation.ui.component.PhotoErrorContent
import com.kslim.presentation.ui.component.PhotoExplorerTopBar
import com.kslim.presentation.ui.photodetail.component.DetailStatChip
import com.kslim.presentation.ui.photodetail.component.HashTagSection
import com.kslim.presentation.ui.photodetail.component.UserInfoSection
import kotlinx.coroutines.launch

@Composable
fun PhotoDetailScreen(
    onBackClick: () -> Unit,
    viewModel: PhotoDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                PhotoDetailSideEffect.NavigateBack -> {
                    snackBarHostState.currentSnackbarData?.dismiss()
                    onBackClick()
                }

                is PhotoDetailSideEffect.ShowSnackBar -> {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(effect.message)
                    }
                }
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            viewModel.onIntent(PhotoDetailIntent.DownloadPhoto)
        } else {
            viewModel.onIntent(PhotoDetailIntent.PermissionDenied)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        topBar = {
            PhotoExplorerTopBar(
                title = stringResource(R.string.title_detail),
                showBackButton = true,
                onBackClick = {
                    viewModel.onIntent(PhotoDetailIntent.ClickBack)
                },
                actions = {
                    val photo = state.photo
                    if (photo != null) {
                        IconButton(
                            onClick = {
                                viewModel.onIntent(PhotoDetailIntent.ToggleFavorite(photo))
                            }
                        ) {
                            Icon(
                                imageVector = if (photo.isFavorite) {
                                    Icons.Default.Favorite
                                } else {
                                    Icons.Default.FavoriteBorder
                                },
                                contentDescription = stringResource(R.string.description_favorite),
                                tint = Color(0xFFFF6B7A)
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        PhotoDetailContent(
            modifier = Modifier.padding(padding),
            state = state,
            onDownloadClick = {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                } else {
                    viewModel.onIntent(PhotoDetailIntent.DownloadPhoto)

                }
            }
        )

    }
}

@Composable
fun PhotoDetailContent(
    modifier: Modifier,
    state: PhotoDetailState,
    onDownloadClick: () -> Unit
) {
    when {
        state.isLoading -> {
            Box(modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        // 초기 로드 에러 ( 데이터 없을 때 또는 네트워크 연결이 안되었을 경우
        state.errorMessage != null -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                PhotoErrorContent(
                    message = state.errorMessage,
                )
            }
        }

        state.photo != null -> {
            val photo = state.photo

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    PhotoAsyncImage(
                        imageUrl = state.photo.imageUrl,
                        memoryCacheKey = photo.id,
                        contentDescription = stringResource(R.string.description_photo),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(360.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )

                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .align(Alignment.BottomEnd),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DetailStatChip(
                            value = "%,d".format(photo.likes),
                            icon = Icons.Default.Favorite
                        )
                        DetailStatChip(
                            value = "%,d".format(photo.downloads),
                            icon = Icons.Default.Share
                        )
                        DetailStatChip(
                            value = stringResource(R.string.photo_download),
                            icon = ImageVector.vectorResource(id = R.drawable.ic_download),
                            clickable = photo.localPath == null,
                            onClick = {
                                onDownloadClick()
                            }
                        )
                    }
                }

                if (photo.tags.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    HashTagSection(photo.tags)
                }

                Spacer(modifier = Modifier.height(14.dp))

                UserInfoSection(photo = photo)
            }
        }

        // 데이터가 없는 경우
        else -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                PhotoErrorContent(
                    message = stringResource(R.string.info_photo_list_empty),
                )
            }
        }
    }
}

