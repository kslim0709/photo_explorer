package com.kslim.data.remote.datasource

import com.kslim.data.network.result.ApiResult
import com.kslim.data.remote.response.PhotoDetailResponse
import com.kslim.data.remote.response.PhotoDownloadResponse
import com.kslim.data.remote.response.PhotoResponse
import okhttp3.ResponseBody
import retrofit2.Response


interface PhotoDataSource {

    suspend fun getPhotos(
        page: Int,
        perPage: Int
    ): ApiResult<List<PhotoResponse>>

    suspend fun getPhotoDetail(
        photoId: String
    ): ApiResult<PhotoDetailResponse>

    suspend fun getPhotoDownload(
        photoId: String
    ): ApiResult<PhotoDownloadResponse>

    suspend fun downloadPhoto(
        downloadUrl: String
    ): Response<ResponseBody>
}