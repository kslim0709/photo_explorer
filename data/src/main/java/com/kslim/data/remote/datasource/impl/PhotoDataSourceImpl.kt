package com.kslim.data.remote.datasource.impl

import com.kslim.data.network.result.ApiResult
import com.kslim.data.network.result.safeApiCall
import com.kslim.data.remote.api.UnsplashApi
import com.kslim.data.remote.datasource.PhotoDataSource
import com.kslim.data.remote.response.PhotoDetailResponse
import com.kslim.data.remote.response.PhotoDownloadResponse
import com.kslim.data.remote.response.PhotoResponse
import jakarta.inject.Inject
import okhttp3.ResponseBody
import retrofit2.Response

class PhotoDataSourceImpl @Inject constructor(
    private val api: UnsplashApi
) : PhotoDataSource {

    override suspend fun getPhotos(
        page: Int,
        perPage: Int
    ): ApiResult<List<PhotoResponse>> {
        return safeApiCall { api.getPhotos(page = page, perPage = perPage) }
    }

    override suspend fun getPhotoDetail(
        photoId: String
    ): ApiResult<PhotoDetailResponse> {
        return safeApiCall { api.getPhotoDetail(id = photoId) }
    }

    override suspend fun getPhotoDownload(photoId: String): ApiResult<PhotoDownloadResponse> {
        return safeApiCall { api.getPhotoDownload(id = photoId) }
    }

    override suspend fun downloadPhoto(downloadUrl: String): Response<ResponseBody> {
        return api.downloadPhoto(downloadUrl)
    }
}