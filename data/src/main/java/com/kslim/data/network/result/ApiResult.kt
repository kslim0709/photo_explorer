package com.kslim.data.network.result

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Failure(val error: NetworkError, val exception: Throwable? = null) : ApiResult<Nothing>
}

sealed interface NetworkError {
    data object Connectivity : NetworkError
    data object Timeout : NetworkError
    data object Server : NetworkError
    data object Client : NetworkError
    data object Unknown : NetworkError
}