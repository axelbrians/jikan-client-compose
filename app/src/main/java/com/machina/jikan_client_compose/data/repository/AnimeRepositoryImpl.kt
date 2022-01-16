package com.machina.jikan_client_compose.data.repository

import com.machina.jikan_client_compose.core.Call
import com.machina.jikan_client_compose.core.Endpoints
import com.machina.jikan_client_compose.core.exception.Error.UNKNOWN_ERROR
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.AnimeService
import com.machina.jikan_client_compose.data.remote.dto.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.network.sockets.*
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
  private val client: HttpClient,
  private val call: Call
) : AnimeService {

  override suspend fun getTopAnimeList(page: Int): Resource<List<AnimeTopDtoKtor>> {
    return try {
      val res = client.get<HttpResponse> {
        url {
          protocol = URLProtocol.HTTPS
          host = Endpoints.HOST
          encodedPath = "${Endpoints.TOP_ANIME_URL}/$page"
        }
      }
//    Timber.d("top anime url ${res.call.request.url.fullPath}")

      if (res.status.isSuccess()) {
        val body = res.receive<AnimeTopResponse>()
        Resource.Success(body.top)
      } else {
        Resource.Error(UNKNOWN_ERROR)
      }
    } catch (e: Exception) {
      when (e) {
        is ClientRequestException -> Resource.Error(e.message)
        is ConnectTimeoutException -> Resource.Error(e.message ?: UNKNOWN_ERROR)
        else -> Resource.Error(UNKNOWN_ERROR)
      }
    }
  }

  override suspend fun searchAnime(query: String, page: Int): Resource<List<ContentSearchDtoKtor>> {
    return try {
      val res = client.request<HttpResponse> {
        method = HttpMethod.Get
        url {
          protocol = URLProtocol.HTTPS
          host = Endpoints.HOST
          encodedPath = Endpoints.SEARCH_ANIME_URL
          parameter("q", query)
          parameter("page", page)
        }
      }

      if (res.status.isSuccess()) {
        val body = res.receive<ContentSearchResponse>()
        Resource.Success(body.results)
      } else {
        Resource.Error(UNKNOWN_ERROR)
      }
    } catch (e: Exception) {
      when (e) {
        is ClientRequestException -> Resource.Error(e.message)
        is ConnectTimeoutException -> Resource.Error(e.message ?: UNKNOWN_ERROR)
        else -> Resource.Error(UNKNOWN_ERROR)
      }
    }
  }

  override suspend fun getAnimeDetails(malId: Int): Resource<ContentDetailsDto> {
    val request = HttpRequestBuilder().apply {
      method = HttpMethod.Get
      url {
        protocol = URLProtocol.HTTPS
        host = Endpoints.HOST
        encodedPath = Endpoints.DETAILS_ANIME_URL + "/$malId"
      }
    }

    return call.invoke(client, request)
  }


}