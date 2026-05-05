package com.kslim.presentation.ui.model

data class PhotoUserProfile(
    val name: String,
    val profileImageUrl: String,
    val instagramUsername: String? = null,
    val twitterUsername: String? = null
)