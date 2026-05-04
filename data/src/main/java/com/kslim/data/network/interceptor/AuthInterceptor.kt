package com.kslim.data.network.interceptor

import com.kslim.data.network.NetworkConstants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newBuilder = chain.request().newBuilder().addHeader("Authorization", "Client-ID ${NetworkConstants.ACCESS_KEY}")
        return chain.proceed(newBuilder.build())
    }

}