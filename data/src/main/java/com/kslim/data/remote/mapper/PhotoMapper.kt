package com.kslim.data.remote.mapper

import com.kslim.data.remote.response.PhotoResponse
import com.kslim.data.remote.response.UrlsResponse
import com.kslim.data.remote.response.UserResponse
import com.kslim.data.remote.response.UserResponse.ProfileImage
import com.kslim.domain.model.Photo
import com.kslim.domain.model.Urls
import com.kslim.domain.model.User


fun PhotoResponse.toDomain(isFavorite: Boolean = false): Photo {
    return Photo(id = this.id,
        urls = this.urls?.toDomain(),
        user = this.user?.toDomain(),
        width = this.width,
        height = this.height,
        isFavorite = isFavorite
    )
}

fun UrlsResponse.toDomain(): Urls {
    return Urls(raw = this.raw,
        full = this.full,
        regular = this.raw,
        small = this.raw,
        thumb = this.raw
    )
}

fun UserResponse.toDomain(): User {
    return User(id = this.id,
        username = this.username,
        name = this.name,
        portfolioUrl = this.portfolioUrl,
        bio = this.bio,
        location = this.location,
        profileImage = this.profileImage?.toDomain(),
        links = this.links?.toDomain(),
        twitterUsername = this.twitterUsername,
        instagramUsername = this.instagramUsername
    )
}

fun ProfileImage.toDomain(): User.ProfileImage {
    return User.ProfileImage(small = this.small,
        medium = this.medium,
        large = this.large
    )
}

fun UserResponse.UserLinks.toDomain(): User.UserLinks {
    return User.UserLinks(small = this.small,
        medium = this.medium,
        large = this.large
    )
}