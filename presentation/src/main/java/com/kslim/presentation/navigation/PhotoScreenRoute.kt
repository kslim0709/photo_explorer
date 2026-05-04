package com.kslim.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface PhotoScreenRoute {
    @Serializable
    data object PhotoList : PhotoScreenRoute

    @Serializable
    data object PhotoFavorite : PhotoScreenRoute

    @Serializable
    data class PhotoDetail(val photoId: String) : PhotoScreenRoute

}