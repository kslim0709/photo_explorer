package com.kslim.data.remote.api

import com.kslim.data.remote.response.PhotoDetailResponse
import com.kslim.data.remote.response.PhotoDownloadResponse
import com.kslim.data.remote.response.PhotoResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

interface UnsplashApi {

    @GET("/photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<PhotoResponse>

    @GET("/photos/{id}")
    suspend fun getPhotoDetail(
        @Path("id") id: String
    ): PhotoDetailResponse

    @GET("/photos/{id}/download")
    suspend fun getPhotoDownload(
        @Path("id") id: String
    ): PhotoDownloadResponse

    @GET
    @Streaming
    suspend fun downloadPhoto(@Url downloadUrl: String): Response<ResponseBody>
}