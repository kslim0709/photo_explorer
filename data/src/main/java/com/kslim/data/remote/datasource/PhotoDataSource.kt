package com.kslim.data.remote.datasource

import com.kslim.data.remote.response.PhotoDetailResponse
import com.kslim.data.remote.response.PhotoResponse


interface PhotoDataSource {

    suspend fun getPhotos(
        page: Int,
        perPage: Int
    ): List<PhotoResponse>

    suspend fun getPhotoDetail(
        photoId: String
    ): PhotoDetailResponse
}