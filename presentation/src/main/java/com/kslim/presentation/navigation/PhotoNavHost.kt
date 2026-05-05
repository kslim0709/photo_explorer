package com.kslim.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kslim.presentation.ui.photodetail.PhotoDetailScreen
import com.kslim.presentation.ui.photofavorite.PhotoFavoriteScreen
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
        // 메인 화면 ( Photo List )
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

        // 솬심 목록 리스트
        composable<PhotoScreenRoute.PhotoFavorite> {
            PhotoFavoriteScreen(
                onBackClick = {
                    navController.popBackStack()

                },
                onNavigateToDetail = { photoId ->
                    navController.navigate(PhotoScreenRoute.PhotoDetail(photoId = photoId))
                }
            )
        }

        // 상세 화면
        composable<PhotoScreenRoute.PhotoDetail> {
            PhotoDetailScreen(
                onBackClick = {
                    navController.popBackStack()

                }
            )
        }
    }
}