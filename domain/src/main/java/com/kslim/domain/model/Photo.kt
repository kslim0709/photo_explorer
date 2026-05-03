package com.kslim.domain.model

data class Photo(
    val id: String,
    val urls: Urls? = null,
    val user: User? = null,
    val width: Int = 0,
    val height: Int = 0
)

data class Urls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)

data class User(
    val id: String,
    val username: String,
    val name: String,
    val portfolioUrl: String,
    val bio: String,
    val location: String,
    val profileImage: ProfileImage? = null,
    val links: UserLinks? = null
) {

    data class ProfileImage(
        val small: String,
        val medium: String,
        val large: String
    )

    data class UserLinks(
        val small: String,
        val medium: String,
        val large: String
    )
}
