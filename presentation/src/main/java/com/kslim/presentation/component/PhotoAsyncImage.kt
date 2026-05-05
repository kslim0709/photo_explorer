package com.kslim.presentation.component

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kslim.presentation.R


@Composable
fun PhotoAsyncImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String?,
    memoryCacheKey: String = imageUrl,
    diskCacheKey: String = imageUrl,
    placeholderRes: Int = R.drawable.ic_launcher_foreground,
    errorRes: Int = R.drawable.ic_nodata
) {
    val context = LocalContext.current

    AsyncImage(
        model = if (LocalInspectionMode.current) {
            R.drawable.ic_launcher_foreground
        } else {
            ImageRequest.Builder(context)
                .data(imageUrl)
                .memoryCacheKey(memoryCacheKey)
                .diskCacheKey(diskCacheKey)
                .crossfade(false)
                .build()
        },
        contentDescription = contentDescription,
        contentScale = contentScale,
        placeholder = painterResource(placeholderRes),
        error = painterResource(errorRes),
        modifier = modifier
            .background(Color.LightGray)
    )
}