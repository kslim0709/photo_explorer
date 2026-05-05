package com.kslim.presentation.ui.photodetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kslim.presentation.R
import com.kslim.presentation.ui.component.PhotoAsyncImage
import com.kslim.presentation.ui.model.PhotoUiModel

@Composable
fun UserInfoSection(
    photo: PhotoUiModel.PhotoDetail
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
            )
            .border(
                width = 1.dp,
                color = Color(0xFFE5E7EB),
                shape = RoundedCornerShape(20.dp)
            )
            .background(
                color = Color(0xFFF9FAFB),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                PhotoAsyncImage(
                    imageUrl = photo.userProfile.profileImageUrl,
                    memoryCacheKey = "${photo.id}_profile",
                    contentDescription = stringResource(R.string.description_photo),
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape),
                    placeholderRes = R.drawable.ic_account_box
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = photo.userProfile.name,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall
                    )
                    if (photo.userProfile.instagramUsername.isNullOrEmpty().not()) {
                        Text(
                            text = "Instagram @${photo.userProfile.instagramUsername}",
                            color = Color(0xFF4A4A4A),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    if (photo.userProfile.twitterUsername.isNullOrEmpty().not()) {
                        Text(
                            text = "Twitter @${photo.userProfile.twitterUsername}",
                            color = Color(0xFF4A4A4A),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Description",
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF6B7280)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = photo.description.ifBlank { "No description provided." },
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF111827),
                lineHeight = 22.sp
            )
        }
    }
}
