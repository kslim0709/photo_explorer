package com.kslim.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kslim.presentation.ui.photolist.PhotoListScreen


@Composable
fun PhotoNavHost(
    modifier: Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = PhotoScreenRoute.PhotoList
    ) {
        composable<PhotoScreenRoute.PhotoList> {
            PhotoListScreen(
                onNavigateToDetail = { photoId ->
                    navController.navigate(PhotoScreenRoute.PhotoDetail(photoId = photoId))

                },
                onNavigateToFavorite = {
                    navController.navigate(PhotoScreenRoute.PhotoFavorite)

                }
            )
        }
    }
}