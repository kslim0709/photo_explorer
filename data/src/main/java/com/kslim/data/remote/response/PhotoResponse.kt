package com.kslim.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoResponse(
    @SerialName("id")
    val id: String = "",
    @SerialName("urls")
    val urls: UrlsResponse? = null,
    @SerialName("user")
    val user: UserResponse? = null,
    @SerialName("width")
    val width: Int = 0,
    @SerialName("height")
    val height: Int = 0
)


@Serializable
data class UrlsResponse(
    @SerialName("raw") val raw: String = "",
    @SerialName("full") val full: String = "",
    @SerialName("regular") val regular: String = "",
    @SerialName("small") val small: String = "",
    @SerialName("thumb") val thumb: String = ""
)

@Serializable
data class UserResponse(
    @SerialName("id") val id: String,
    @SerialName("username") val username: String = "",
    @SerialName("name") val name: String = "",
    @SerialName("portfolio_url") val portfolioUrl: String = "",
    @SerialName("bio") val bio: String = "",
    @SerialName("location") val location: String = "",
    @SerialName("profile_image") val profileImage: ProfileImage? = null,
    @SerialName("links") val links: UserLinks? = null
) {

    @Serializable
    data class ProfileImage(
        @SerialName("small") val small: String = "",
        @SerialName("medium") val medium: String = "",
        @SerialName("large") val large: String = ""
    )

    @Serializable
    data class UserLinks(
        @SerialName("small") val small: String = "",
        @SerialName("medium") val medium: String = "",
        @SerialName("large") val large: String = ""
    )
}
