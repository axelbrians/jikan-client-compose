package com.machina.jikan_client_compose.data.network

import com.machina.jikan_client_compose.data.exception.Error.TIMEOUT_ERROR
import com.machina.jikan_client_compose.data.exception.Error.UNKNOWN_ERROR
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
                Resource.success(body)
            } else if (errorBody != null) {
                Resource.error(UNKNOWN_ERROR, null)
            } else {
                Resource.error(UNKNOWN_ERROR, null)
            }

        } catch (e: Exception) {
            when(e) {
                is SocketTimeoutException -> Resource.error(TIMEOUT_ERROR,null)
                else -> Resource.error(UNKNOWN_ERROR, null)
            }
        }
    }

    suspend fun <T, R, U> enqueue(req: T, converter: (ResponseBody) -> U, call: suspend (T) -> Response<R>): Resource<R>  {
        return try {
            val res = call(req)
            val body = res.body()
            val errorBody = res.errorBody()

            if (res.isSuccessful && body != null) {
                Resource.success(body)
            } else if (errorBody != null) {
                val parsedError = converter(errorBody)
                if (parsedError != null) {
                    Resource.error("", null)
                } else {
                    Resource.error(UNKNOWN_ERROR, null)
                }
            } else {
                Resource.error(UNKNOWN_ERROR, null)
            }

        } catch (e: Exception) {
            when(e) {
                is SocketTimeoutException -> Resource.error(TIMEOUT_ERROR,null)
                else -> Resource.error(UNKNOWN_ERROR, null)
            }
        }
    }

    suspend fun <S, T, R, U> enqueue(req1: S, req2: T, converter: (ResponseBody) -> U, call: suspend (S, T) -> Response<R>): Resource<R>  {
        return try {
            val res = call(req1, req2)
            val body = res.body()
            val errorBody = res.errorBody()
            if (res.isSuccessful && body != null) {
                Resource.success(body)
            } else if (errorBody != null) {
                val parsedError = converter(errorBody)
                if (parsedError != null) {
                    Resource.error("", null)
                } else {
                    Resource.error(UNKNOWN_ERROR, null)
                }
            } else {
                Resource.error(UNKNOWN_ERROR, null)
            }

        } catch (e: Exception) {
            when(e) {
                is SocketTimeoutException -> Resource.error(TIMEOUT_ERROR,null)
                else -> Resource.error(UNKNOWN_ERROR, null)
            }
        }
    }
}