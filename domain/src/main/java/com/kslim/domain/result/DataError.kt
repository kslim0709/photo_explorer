package com.kslim.domain.result


sealed interface DataError {
    data object Network : DataError
    data object Timeout : DataError
    data object Unauthorized : DataError
    data object Forbidden : DataError
    data object NotFound : DataError
    data object Server : DataError
    data class Unknown(val message: String? = null) : DataError
}