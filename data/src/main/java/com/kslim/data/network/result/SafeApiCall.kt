package com.kslim.data.network.result

import com.kslim.domain.result.DataError
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend inline fun <T : Any?> safeApiCall(
    crossinline block: suspend () -> T
): ApiResult<T> {
    return try {
        ApiResult.Success(block())
    } catch (e: Exception) {
        ApiResult.Failure(
            error = when (e) {
                is UnknownHostException -> NetworkError.Connectivity
                is SocketTimeoutException -> NetworkError.Timeout
                is HttpException -> when (e.code()) {
                    in 400..499 -> NetworkError.Client
                    else -> NetworkError.Server
                }
                is IOException -> NetworkError.Connectivity
                else -> NetworkError.Unknown
            },
            exception = e
        )
    }
}


fun Throwable?.toDataError(): DataError {
    return when (this) {
        is HttpException -> when (code()) {
            401 -> DataError.Unauthorized
            403 -> DataError.Forbidden
            404 -> DataError.NotFound
            in 500..599 -> DataError.Server
            else -> DataError.Unknown(message ?: "Unknow Error")
        }

        is UnknownHostException -> DataError.Network
        is SocketTimeoutException -> DataError.Timeout
        is IOException -> DataError.Network

        else -> DataError.Unknown(message = this?.message ?: "Unknow Error")
    }
}