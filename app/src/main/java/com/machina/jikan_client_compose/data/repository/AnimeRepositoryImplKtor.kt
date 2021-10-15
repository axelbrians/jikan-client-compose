package com.machina.jikan_client_compose.data.repository

import com.machina.jikan_client_compose.core.Endpoints
import com.machina.jikan_client_compose.core.exception.Error
import com.machina.jikan_client_compose.core.exception.Error.UNKNOWN_ERROR
import com.machina.jikan_client_compose.data.network.Resource
import com.machina.jikan_client_compose.data.remote.AnimeServiceKtor
import com.machina.jikan_client_compose.data.remote.dto.AnimeTopDtoKtor
import com.machina.jikan_client_compose.data.remote.dto.AnimeTopResponseKtor
import com.machina.jikan_client_compose.data.remote.dto.ContentSearchDtoKtor
import com.machina.jikan_client_compose.data.remote.dto.ContentSearchResponseKtor
import com.machina.jikan_client_compose.domain.model.ContentSearch
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.network.sockets.*
import timber.log.Timber
import javax.inject.Inject

class AnimeRepositoryImplKtor @Inject constructor(
  private val client: HttpClient,
) : AnimeServiceKtor {

  override suspend fun getTopAnimeList(): Resource<List<AnimeTopDtoKtor>> {
    return try {
      val res = client.get<HttpResponse> {
        url {
          protocol = URLProtocol.HTTPS
          host = Endpoints.HOST
          encodedPath = "/${Endpoints.TOP_ANIME_URL}"
        }
      }
//    Timber.d("top anime url ${res.call.request.url.fullPath}")

      if (res.status.isSuccess()) {
        val body = res.receive<AnimeTopResponseKtor>()
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
      val res = client.get<HttpResponse> {
        url {
          protocol = URLProtocol.HTTPS
          host = Endpoints.HOST
          encodedPath = Endpoints.SEARCH_ANIME_URL
          parameter("q", query)
          parameter("page", page)
        }
      }


      if (res.status.isSuccess()) {
        val body = res.receive<ContentSearchResponseKtor>()
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


}