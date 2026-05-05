package com.kslim.domain.model

data class FavoritePhoto(
    val id: String,
    val imageUrl: String,                // Urls 중 regular
    val userName: String,                // User user name
    val userProfileImageUrl: String,     // User profileImage > small url
    val isFavorite: Boolean,
    val localPath: String? = null        // 다운로드 경로
)