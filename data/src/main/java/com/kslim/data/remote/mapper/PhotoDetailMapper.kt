package com.kslim.data.remote.mapper

import com.kslim.data.remote.response.PhotoDetailResponse
import com.kslim.data.remote.response.TagResponse
import com.kslim.domain.model.PhotoDetail
import com.kslim.domain.model.PhotoTag


fun PhotoDetailResponse.toDomain(isFavorite: Boolean = false): PhotoDetail {
    return PhotoDetail(
        id = this.id,
        likes = this.likes,
        downloads = this.downloads,
        views = this.views,
        description = this.description,
        urls = this.urls?.toDomain(),
        user = this.user?.toDomain(),
        tags = this.tags.map { it.toDomain() },
        isFavorite = isFavorite
    )
}


fun TagResponse.toDomain(): PhotoTag {
    return PhotoTag(title = this.title)
}