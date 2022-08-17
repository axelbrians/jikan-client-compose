package com.machina.jikan_client_compose.data.repository

import com.machina.jikan_client_compose.core.Endpoints
import com.machina.jikan_client_compose.core.SafeCall
import com.machina.jikan_client_compose.core.error.GeneralError
import com.machina.jikan_client_compose.core.error.MyError
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.MangaService
import com.machina.jikan_client_compose.data.remote.dto.content_search.ContentSearchResponse
import com.machina.jikan_client_compose.data.remote.dto_v4.manga_details.MangaDetailsDtoV4
import com.machina.jikan_client_compose.data.remote.dto_v4.manga_details.MangaDetailsResponseV4
import com.machina.jikan_client_compose.di.AndroidKtorClient
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MangaRepository @Inject constructor(
  @AndroidKtorClient private val client: HttpClient,
  private val safeCall: SafeCall
): MangaService {

  // TODO: Need regulated class for manga and anime search, currently just focus on anime
  override suspend fun searchManga(query: String, page: Int): Resource<ContentSearchResponse> {
    val request = HttpRequestBuilder().apply {
      method = HttpMethod.Get
      url {
        protocol = URLProtocol.HTTPS
        host = Endpoints.HOST_V4
        encodedPath = Endpoints.MANGA_SEARCH
        parameter("q", query)
        parameter("page", page)
      }
    }

    return safeCall<ContentSearchResponse, GeneralError>(client, request)
  }

  override suspend fun getMangaDetails(malId: Int): Resource<MangaDetailsDtoV4> {
    val request = HttpRequestBuilder().apply {
      method = HttpMethod.Get
      url {
        protocol = URLProtocol.HTTPS
        host = Endpoints.HOST_V4
        encodedPath = Endpoints.MANGA_DETAILS + "/$malId"
      }
    }

    val res = safeCall<MangaDetailsResponseV4, GeneralError>(client, request)

    return if (res is Resource.Success && res.data != null) {
      Resource.Success(res.data.data)
    } else {
      Resource.Error(res.message ?: MyError.UNKNOWN_ERROR)
    }
  }
}