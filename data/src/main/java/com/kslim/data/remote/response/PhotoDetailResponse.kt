package com.kslim.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoDetailResponse(
    @SerialName("id") val id: String = "",
    @SerialName("downloads") val downloads: Int = 0,
    @SerialName("exif") val exif: ExifResponse? = null,
    @SerialName("location") val location: LocationResponse? = null,
    @SerialName("tags") val tags: List<TagResponse> = emptyList(),
)

@Serializable
data class ExifResponse(
    @SerialName("make") val make: String = "",
    @SerialName("model") val model: String = "",
    @SerialName("exposure_time") val exposureTime: String = "",
    @SerialName("aperture") val aperture: String = "",
    @SerialName("focal_length") val focalLength: String = "",
    @SerialName("iso") val iso: Int = 0
)

@Serializable
data class LocationResponse(
    @SerialName("city") val city: String = "",
    @SerialName("country") val country: String = "",
    @SerialName("position") val position: PositionResponse? = null
)

@Serializable
data class PositionResponse(
    @SerialName("latitude") val latitude: Double = 0.0,
    @SerialName("longitude") val longitude: Double = 0.0
)

@Serializable
data class TagResponse(
    @SerialName("title") val title: String = ""
)