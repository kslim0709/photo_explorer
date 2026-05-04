package com.kslim.presentation.ui.photolist.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kslim.presentation.R
import com.kslim.presentation.theme.PhotoExplorerTheme
import com.kslim.presentation.ui.photolist.model.PhotoUiModel

@Composable
fun PhotoGridItem(
    photo: PhotoUiModel,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF24262B)
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = if (LocalInspectionMode.current) {
                    R.drawable.ic_launcher_foreground
                } else {
                    ImageRequest.Builder(context)
                        .data(photo.imageUrl)
                        .crossfade(true)
                        .build()
                },
                contentDescription = "Photo Image",
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.ic_nodata),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()

                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.6f)
                            ),
                            startY = 300f
                        )
                    )
                    .height(68.dp)
                    .padding(horizontal = 12.dp)
                    .align(Alignment.BottomEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = if (LocalInspectionMode.current) {
                        R.drawable.ic_launcher_foreground
                    } else {
                        ImageRequest.Builder(context)
                            .data(photo.userProfileImageUrl)
                            .crossfade(true)
                            .build()
                    },
                    contentDescription = photo.userName,
                    error = painterResource(R.drawable.ic_nodata),
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = Color.White,
                            shape = CircleShape
                        ),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "by ${photo.userName}",
                    color = Color.White,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = onFavoriteClick
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
    }
}

@Preview(showBackground = true)
@Composable
fun PhotoGridItemPreview() {
    PhotoExplorerTheme {
        PhotoGridItem(
            photo = PhotoUiModel(
                id = "1",
                imageUrl = "",
                userName = "Alex Smith",
                userProfileImageUrl = "",
                isFavorite = true
            ),
            onClick = {},
            onFavoriteClick = {}
        )
    }
}