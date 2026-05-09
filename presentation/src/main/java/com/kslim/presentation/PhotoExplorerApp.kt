package com.kslim.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.kslim.presentation.ui.component.NetworkStatusBanner
import com.kslim.presentation.navigation.PhotoDoubleBackToExit
import com.kslim.presentation.navigation.PhotoNavHost


@Composable
fun PhotoExplorerApp() {
    val navController = rememberNavController()

    Box(modifier = Modifier.fillMaxSize()) {
        PhotoNavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController
        )

        NetworkStatusBanner()
        PhotoDoubleBackToExit(navController)
    }
}