package com.kslim.presentation.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Precision
import com.kslim.presentation.R


@Composable
fun PhotoAsyncImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String?,
    memoryCacheKey: String = imageUrl,
    placeholderRes: Int = R.drawable.ic_launcher_foreground,
    errorRes: Int = R.drawable.ic_nodata
) {
    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current

    val imageRequest = remember(imageUrl, memoryCacheKey) {
        ImageRequest.Builder(context)
            .data(imageUrl)
            .memoryCacheKey(memoryCacheKey)
            .diskCachePolicy(CachePolicy.DISABLED)
            .precision(Precision.INEXACT)
            .crossfade(false)
            .build()
    }

    AsyncImage(
        model = if (isPreview) {
            R.drawable.ic_launcher_foreground
        } else {
            imageRequest
        },
        contentDescription = contentDescription,
        contentScale = contentScale,
        placeholder = painterResource(placeholderRes),
        error = painterResource(errorRes),
        modifier = modifier
    )
}