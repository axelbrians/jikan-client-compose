package com.machina.jikan_client_compose.data.repository

import com.machina.jikan_client_compose.core.SafeCall
import com.machina.jikan_client_compose.core.Endpoints
import com.machina.jikan_client_compose.core.error.GeneralError
import com.machina.jikan_client_compose.core.exception.MyError.UNKNOWN_ERROR
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.AnimeService
import com.machina.jikan_client_compose.data.remote.dto.*
import com.machina.jikan_client_compose.domain.model.AnimeTop
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.network.sockets.*
import javax.inject.Inject

class AnimeRepository @Inject constructor(
  private val client: HttpClient,
  private val safeCall: SafeCall
) : AnimeService {

  override suspend fun getTopAnimeList(page: Int): Resource<AnimeTopResponse> {
    val request = HttpRequestBuilder().apply {
      method = HttpMethod.Get
      url {
        protocol = URLProtocol.HTTPS
        host = Endpoints.HOST
        encodedPath = "${Endpoints.TOP_ANIME_URL}/$page"
      }
    }

    return safeCall<AnimeTopResponse, GeneralError>(client, request)
  }

  override suspend fun searchAnime(query: String, page: Int): Resource<ContentSearchResponse> {
    val request = HttpRequestBuilder().apply {
      method = HttpMethod.Get
      url {
        protocol = URLProtocol.HTTPS
        host = Endpoints.HOST
        encodedPath = Endpoints.SEARCH_ANIME_URL
        parameter("q", query)
        parameter("page", page)
      }
    }

    return safeCall<ContentSearchResponse, GeneralError>(client, request)
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

    return safeCall<ContentDetailsDto, GeneralError>(client, request)
  }


}