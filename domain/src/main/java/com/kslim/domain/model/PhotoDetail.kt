package com.kslim.domain.model

data class PhotoDetail(
    val id: String,
    val likes: Int,
    val downloads: Int,
    val views: Int,
    val description: String,
    val urls: Urls? = null,
    val user: User? = null,
    val tags: List<PhotoTag>,
    val isFavorite: Boolean
)

data class PhotoTag(
    val title: String
)