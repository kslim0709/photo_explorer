package com.kslim.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoStatisticResponse(
    @SerialName("id") val id: String = "",
    @SerialName("views") val views: StatCategoryResponse? = null,
)

@Serializable
data class StatCategoryResponse(
    @SerialName("total") val total: Int = 0,
    @SerialName("historical") val historical: HistoricalResponse? = null
)

@Serializable
data class HistoricalResponse(
    @SerialName("change") val change: Int = 0,
    @SerialName("resolution") val resolution: String = "",
    @SerialName("quantity") val quantity: Int = 0,
    @SerialName("values") val values: List<DataPointResponse> = emptyList()
)

@Serializable
data class DataPointResponse(
    @SerialName("date") val date: String = "",
    @SerialName("value") val value: Int = 0
)