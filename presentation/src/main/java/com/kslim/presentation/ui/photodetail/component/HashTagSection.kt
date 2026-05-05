package com.kslim.presentation.ui.photodetail.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HashTagSection(
    hashTags: List<String>
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(hashTags) { tag ->
            Surface(
                color = Color(0xFFE8F1FF),
                shape = RoundedCornerShape(999.dp),
                border = BorderStroke(1.dp, Color(0xFFBBD7FF))
            ) {
                Text(
                    text = "#$tag",
                    color = Color(0xFF4B5563),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}