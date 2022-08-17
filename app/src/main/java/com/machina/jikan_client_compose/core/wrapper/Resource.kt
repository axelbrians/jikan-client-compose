package com.machina.jikan_client_compose.core.wrapper

import com.machina.jikan_client_compose.core.error.MyError

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(
        data: T,
        message: String? = null
    ): Resource<T>(data)

    class Error<T>(
        message: String?,
        data: T? = null
    ) : Resource<T>(data, message ?: MyError.UNKNOWN_ERROR)

    class Loading<T>(data: T? = null) : Resource<T>(data)
}
