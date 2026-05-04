package com.kslim.presentation.ui.photolist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun PhotoListScreen(
    paddingValues: PaddingValues,
    viewModel: PhotoListViewModel = hiltViewModel(),
) {

    Greeting(
        name = "안녕하세요.",
        modifier = Modifier.padding(paddingValues)
    )

    viewModel.loadPhotos()

}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}