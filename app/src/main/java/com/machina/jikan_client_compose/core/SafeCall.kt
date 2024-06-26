package com.machina.jikan_client_compose.core

import com.machina.jikan_client_compose.core.error.GeneralError
import com.machina.jikan_client_compose.core.error.MyError
import com.machina.jikan_client_compose.core.wrapper.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.ktor.network.sockets.ConnectTimeoutException
import kotlinx.coroutines.delay
import timber.log.Timber

object SafeCall {
	const val RetryCount = 5
}

suspend inline fun <reified T: Any, reified U: Any> HttpClient.safeCall(
	request: HttpRequestBuilder,
	retryCount: Int = 0
): Resource<T> {
	return try {
		val res = if (retryCount > 0) {
			callWithRetry(this, request, retryCount)
		} else {
			this.request(request)
		}

		if (res.status.isSuccess()) {
			val body = res.receive<T>()
			Resource.Success(body)
		} else {
			when (val error = res.receive<U>()) {
				is GeneralError -> Resource.Error(error.message)
				else -> Resource.Error(MyError.UNKNOWN_ERROR)
			}
		}
	} catch (e: Exception) {
		Timber.d(e.message)
		when (e) {
			is ClientRequestException -> Resource.Error(e.message)
			is ConnectTimeoutException -> Resource.Error(e.message)
			else -> Resource.Error(MyError.UNKNOWN_ERROR)
		}
	}
}

//  suspend inline fun <reified T: Any, reified U: Any> invokeWithRetry(
//    client: HttpClient,
//    request: HttpRequestBuilder,
//    retryCount: Int = 5
//  ): Resource<T> {
//    return try {
//      val res = callWithRetry(client, request, retryCount)
//
//      if (res.status.isSuccess()) {
//        val body = res.receive<T>()
//        Resource.Success(body)
//      } else {
//        when (val error = res.receive<U>()) {
//          is GeneralError -> Resource.Error(error.message)
//          else -> Resource.Error(MyError.UNKNOWN_ERROR)
//        }
//      }
//    } catch (e: Exception) {
//      Timber.e("Error class:\n${e.javaClass.name}")
//      Timber.e("${e.message}")
//      when (e) {
//        is ClientRequestException -> Resource.Error(e.message)
//        is ConnectTimeoutException -> Resource.Error(e.message ?: MyError.UNKNOWN_ERROR)
//        else -> Resource.Error(MyError.UNKNOWN_ERROR)
//      }
//    }
//  }

/**
 * Function to retry HttpCalls if the server responded with a 429
 * [HttpStatusCode.TooManyRequests]. Will return at the first call when it was not a 429.
 *
 * @param client The HttpClient to use for the call.
 * @param request The HttpRequestBuilder.
 * @param times The amount of times to retry.
 *
 * @return HttpResponse The response of the call.
 */
suspend inline fun callWithRetry(
	client: HttpClient,
	request: HttpRequestBuilder,
	times: Int
): HttpResponse {
	var res = client.request<HttpResponse>(request)
//    Timber.v(res.call.response.toString())
//    Timber.v(res.status.toString())
//    Timber.v(res.status.value.toString())
	repeat(times) {
		if (res.status.value != HttpStatusCode.TooManyRequests.value) {
			return res
		} else {
			delay(1000L)
			res = client.request(request)
		}
	}

	// Return last fetched response, regardless of status code
	return res
}
