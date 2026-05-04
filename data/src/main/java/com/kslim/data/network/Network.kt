package com.kslim.data.network

import retrofit2.Retrofit


interface Network {
    fun <T> create(baseUrl: String, service: Class<T>): T
}

inline fun <reified T> Network.create(baseUrl: String): T {
    return create(baseUrl, T::class.java)
}

class NetworkImpl(
    private val retrofit: Retrofit.Builder
) : Network {
    override fun <T> create(baseUrl: String, service: Class<T>): T =
        retrofit.baseUrl(baseUrl).build().create(service)

}