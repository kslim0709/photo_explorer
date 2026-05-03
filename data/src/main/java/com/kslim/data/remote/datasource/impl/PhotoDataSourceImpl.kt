package com.kslim.data.remote.datasource.impl

import com.kslim.data.remote.api.UnsplashApi
import com.kslim.data.remote.datasource.PhotoDataSource
import com.kslim.data.remote.response.PhotoDetailResponse
import com.kslim.data.remote.response.PhotoResponse
import jakarta.inject.Inject

class PhotoDataSourceImpl @Inject constructor(
    private val api: UnsplashApi
) : PhotoDataSource {

    override suspend fun getPhotos(
        page: Int,
        perPage: Int
    ): List<PhotoResponse> {
        return api.getPhotos(
            page = page,
            perPage = perPage
        )
    }

    override suspend fun getPhotoDetail(
        photoId: String
    ): PhotoDetailResponse {
        return api.getPhotoDetail(id = photoId)
    }
}