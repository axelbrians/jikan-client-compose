package com.machina.jikan_client_compose.data.network

import com.machina.jikan_client_compose.core.exception.Error.TIMEOUT_ERROR
import com.machina.jikan_client_compose.core.exception.Error.UNKNOWN_ERROR
import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber
import java.net.SocketTimeoutException

class SafeCall {

    suspend fun <R> enqueue(call: suspend () -> Response<R>): Resource<R>  {
        return try {
            val res = call()
            val body = res.body()
            val errorBody = res.errorBody()

            Timber.d("body $body")

            if (res.isSuccessful && body != null) {
                Resource.Success(body)
            } else if (errorBody != null) {
                Resource.Error(UNKNOWN_ERROR, null)
            } else {
                Resource.Error(UNKNOWN_ERROR, null)
            }

        } catch (e: Exception) {
            when(e) {
                is SocketTimeoutException -> Resource.Error(TIMEOUT_ERROR,null)
                else -> Resource.Error(UNKNOWN_ERROR, null)
            }
        }
    }

    suspend fun <S, T, U> enqueue(req: S, converter: (ResponseBody) -> U, call: suspend (S) -> Response<T>): Resource<T>  {
        return try {
            val res = call(req)
            val body = res.body()
            val errorBody = res.errorBody()

            if (res.isSuccessful && body != null) {
                Resource.Success(body)
            } else if (errorBody != null) {
                val parsedError = converter(errorBody)
                if (parsedError != null) {
                    Resource.Error("", null)
                } else {
                    Resource.Error(UNKNOWN_ERROR, null)
                }
            } else {
                Resource.Error(UNKNOWN_ERROR, null)
            }

        } catch (e: Exception) {
            when(e) {
                is SocketTimeoutException -> Resource.Error(TIMEOUT_ERROR,null)
                else -> Resource.Error(UNKNOWN_ERROR, null)
            }
        }
    }

    suspend fun <R, T, S, U> enqueue(req1: S, req2: T, converter: (ResponseBody) -> U, call: suspend (S, T) -> Response<R>): Resource<R>  {
        return try {
            val res = call(req1, req2)
            val body = res.body()
            val errorBody = res.errorBody()
            if (res.isSuccessful && body != null) {
                Resource.Success(body)
            } else if (errorBody != null) {
                val parsedError = converter(errorBody)
                if (parsedError != null) {
                    Resource.Error("", null)
                } else {
                    Resource.Error(UNKNOWN_ERROR, null)
                }
            } else {
                Resource.Error(UNKNOWN_ERROR, null)
            }

        } catch (e: Exception) {
            when(e) {
                is SocketTimeoutException -> Resource.Error(TIMEOUT_ERROR,null)
                else -> Resource.Error(UNKNOWN_ERROR, null)
            }
        }
    }

    suspend fun <O, P, Q, R, S> enqueue(
        req1: O, req2: P, req3: Q,
        converter: (ResponseBody) -> S,
        call: suspend (O, P, Q) -> Response<R>
): Resource<R>  {
        return try {
            val res = call(req1, req2, req3)
            val body = res.body()
            val errorBody = res.errorBody()
            if (res.isSuccessful && body != null) {
                Resource.Success(body)
            } else if (errorBody != null) {
                val parsedError = converter(errorBody)
                if (parsedError != null) {
                    Resource.Error("", null)
                } else {
                    Resource.Error(UNKNOWN_ERROR, null)
                }
            } else {
                Resource.Error(UNKNOWN_ERROR, null)
            }

        } catch (e: Exception) {
            when(e) {
                is SocketTimeoutException -> Resource.Error(TIMEOUT_ERROR,null)
                else -> Resource.Error(UNKNOWN_ERROR, null)
            }
        }
    }
}