package com.machina.jikan_client_compose.data.repository

import com.machina.jikan_client_compose.core.Endpoints
import com.machina.jikan_client_compose.core.exception.Error
import com.machina.jikan_client_compose.data.network.Resource
import com.machina.jikan_client_compose.data.remote.AnimeServiceKtor
import com.machina.jikan_client_compose.data.remote.dto.AnimeTopKtorDto
import com.machina.jikan_client_compose.data.remote.dto.AnimeTopKtorResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import timber.log.Timber
import javax.inject.Inject

class AnimeRepositoryImplKtor @Inject constructor(
  private val client: HttpClient,
) : AnimeServiceKtor {

  override suspend fun getTopAnimeList(): Resource<List<AnimeTopKtorDto>> {
    val res = client.get<HttpResponse> {
      url {
        protocol = URLProtocol.HTTPS
        host = Endpoints.HOST
        encodedPath = "/${Endpoints.TOP_ANIME_URL}"
      }
    }

    return try {
      if (res.status.isSuccess()) {
        val body = res.receive<AnimeTopKtorResponse>()
        Resource.Success(body.top)
      } else {
        Resource.Error(Error.UNKNOWN_ERROR)
      }
    } catch (e: Exception) {
      Resource.Error("An error occurred")
    }
  }


}