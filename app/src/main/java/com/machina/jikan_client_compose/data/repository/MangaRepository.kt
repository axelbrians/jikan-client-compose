package com.machina.jikan_client_compose.data.repository

import com.machina.jikan_client_compose.core.Endpoints
import com.machina.jikan_client_compose.core.SafeCall
import com.machina.jikan_client_compose.core.error.GeneralError
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.MangaService
import com.machina.jikan_client_compose.data.remote.dto.content_details.ContentDetailsDto
import com.machina.jikan_client_compose.data.remote.dto.content_search.ContentSearchResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class MangaRepository @Inject constructor(
  private val client: HttpClient,
  private val safeCall: SafeCall
): MangaService {

  override suspend fun searchManga(query: String, page: Int): Resource<ContentSearchResponse> {
    val request = HttpRequestBuilder().apply {
      method = HttpMethod.Get
      url {
        protocol = URLProtocol.HTTPS
        host = Endpoints.HOST_V3
        encodedPath = Endpoints.MANGA_SEARCH
        parameter("q", query)
        parameter("page", page)
      }
    }

    return safeCall<ContentSearchResponse, GeneralError>(client, request)
  }

  override suspend fun getMangaDetails(malId: Int): Resource<ContentDetailsDto> {
    val request = HttpRequestBuilder().apply {
      method = HttpMethod.Get
      url {
        protocol = URLProtocol.HTTPS
        host = Endpoints.HOST_V3
        encodedPath = Endpoints.MANGA_DETAILS + "/$malId"
      }
    }

    return safeCall<ContentDetailsDto, GeneralError>(client, request)
  }
}