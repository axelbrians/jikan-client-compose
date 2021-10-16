package com.machina.jikan_client_compose.core

import com.machina.jikan_client_compose.core.error.GeneralError
import com.machina.jikan_client_compose.core.exception.Error
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.network.sockets.*

class Call {

  suspend inline fun <reified T: Any> invoke(client: HttpClient, request: HttpRequestBuilder): Resource<T> {
    return try {
      val res = client.request<HttpResponse>(request)

      if (res.status.isSuccess()) {
        val body = res.receive<T>()
        Resource.Success(body)
      } else {
        val error = res.receive<GeneralError>()
        Resource.Error(error.message)
      }
    } catch (e: Exception) {
      when (e) {
        is ClientRequestException -> Resource.Error(e.message)
        is ConnectTimeoutException -> Resource.Error(e.message ?: Error.UNKNOWN_ERROR)
        else -> Resource.Error(Error.UNKNOWN_ERROR)
      }
    }
  }
}