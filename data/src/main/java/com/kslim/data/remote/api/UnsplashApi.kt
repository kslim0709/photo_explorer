package com.kslim.data.remote.api

import com.kslim.data.remote.response.PhotoDetailResponse
import com.kslim.data.remote.response.PhotoResponse
import com.kslim.data.remote.response.PhotoStatisticResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {

    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<PhotoResponse>

    @GET("photos/{id}")
    suspend fun getPhotoDetail(
        @Path("id") id: String
    ): PhotoDetailResponse

    @GET("photos/{id}/statistics")
    suspend fun getPhotoStatistics(
        @Path("id") id: String
    ): PhotoStatisticResponse
}