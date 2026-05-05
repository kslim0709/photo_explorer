package com.kslim.presentation.navigation

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController


@Composable
fun PhotoDoubleBackToExit(
    navController: NavHostController
) {
    val context = LocalContext.current
    var backPressedTime by remember { mutableLongStateOf(0L) }

    BackHandler {
        val currentDestination =
            navController.currentBackStackEntry?.destination

        val isPhotoList =
            currentDestination?.hasRoute<PhotoScreenRoute.PhotoList>() == true

        if (!isPhotoList) {
            navController.popBackStack()
            return@BackHandler
        }

        val currentTime = System.currentTimeMillis()

        if (currentTime - backPressedTime <= 2000) {
            (context as? Activity)?.finish()
        } else {
            backPressedTime = currentTime
            Toast.makeText(context, "한 번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show()
        }
    }
}