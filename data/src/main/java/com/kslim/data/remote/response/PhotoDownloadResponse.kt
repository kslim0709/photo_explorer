package com.kslim.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoDownloadResponse(
    @SerialName("url") val downloadUrl: String = ""
)