package com.machina.jikan_client_compose.data.repository

import com.machina.jikan_client_compose.core.Endpoints
import com.machina.jikan_client_compose.core.exception.Error
import com.machina.jikan_client_compose.core.Resource
import com.machina.jikan_client_compose.data.remote.MangaService
import com.machina.jikan_client_compose.data.remote.dto.ContentSearchDtoKtor
import com.machina.jikan_client_compose.data.remote.dto.ContentSearchResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.network.sockets.*
import javax.inject.Inject

class MangaRepositoryImpl @Inject constructor(
  private val client: HttpClient
): MangaService {
  override suspend fun searchManga(query: String, page: Int): Resource<List<ContentSearchDtoKtor>> {
    return try {
      val res = client.get<HttpResponse> {
        url {
          protocol = URLProtocol.HTTPS
          host = Endpoints.HOST
          encodedPath = Endpoints.SEARCH_MANGA_URL
          parameter("q", query)
          parameter("page", page)
        }
      }

      if (res.status.isSuccess()) {
        val body = res.receive<ContentSearchResponse>()
        Resource.Success(body.results)
      } else {
        Resource.Error(Error.UNKNOWN_ERROR)
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