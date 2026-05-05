package com.kslim.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kslim.presentation.R


@Composable
fun PhotoErrorContent(
    message: String,
    showRetryButton: Boolean = false,
    onRetryClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message)

        if (showRetryButton) {
            Spacer(modifier = Modifier.height(12.dp))

            onRetryClick?.let {
                Button(onClick = it) {
                    Text(text = stringResource(R.string.retry))
                }
            }
        }
    }
}