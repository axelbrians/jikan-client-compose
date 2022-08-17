package com.machina.jikan_client_compose.data.repository

import com.machina.jikan_client_compose.core.Endpoints
import com.machina.jikan_client_compose.core.SafeCall
import com.machina.jikan_client_compose.core.error.GeneralError
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.core.wrapper.ResponseDataListWrapper
import com.machina.jikan_client_compose.data.remote.anime_details.AnimeDetailsService
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_characters.AnimeCharacterResponse
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_details.AnimeDetailsDtoV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_details.AnimeDetailsResponseV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_recommendations.AnimeRecommendationsResponse
import com.machina.jikan_client_compose.di.AndroidKtorClient
import com.machina.jikan_client_compose.domain.model.anime.AnimeCharacterModel
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalDataModel
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeDetailsRepository @Inject constructor(
  @AndroidKtorClient private val client: HttpClient,
  private val safeCall: SafeCall
): AnimeDetailsService {

  override suspend fun getAnimeDetails(malId: Int): Resource<AnimeDetailsDtoV4> {
    val request = HttpRequestBuilder().apply {
      method = HttpMethod.Get
      url {
        protocol = URLProtocol.HTTPS
        host = Endpoints.HOST_V4
        encodedPath = Endpoints.ANIME_DETAILS + "/$malId"
      }
    }

    val res = safeCall.invokeWithRetry<AnimeDetailsResponseV4, GeneralError>(client, request)
    return if (res is Resource.Success && res.data != null) {
      Resource.Success(res.data.data)
    } else {
      Resource.Error(res.message)
    }
  }

  override suspend fun getAnimeCharacters(malId: Int): Resource<List<AnimeCharacterModel>> {
    val request = HttpRequestBuilder().apply {
      method = HttpMethod.Get
      url {
        protocol = URLProtocol.HTTPS
        host = Endpoints.HOST_V4
        encodedPath = Endpoints.ANIME_DETAILS + "/$malId" + Endpoints.ANIME_CHARACTERS
      }
    }

    val res = safeCall.invokeWithRetry<ResponseDataListWrapper<AnimeCharacterResponse>, GeneralError>(client, request)

    Timber.tag("AnimeCharacter").d(res.message)
    return if (res is Resource.Success && res.data != null) {
      val orderedCharacters = res.data.data.sortedByDescending { it.favoritesCount }
      Resource.Success(
        orderedCharacters.map {
          AnimeCharacterModel.from(it)
        }
      )
    } else {
      Resource.Error(res.message)
    }
  }

  override suspend fun getAnimeRecommendations(malId: Int): Resource<List<AnimeVerticalDataModel>> {
    val request = HttpRequestBuilder().apply {
      method = HttpMethod.Get
      url {
        protocol = URLProtocol.HTTPS
        host = Endpoints.HOST_V4
        encodedPath = Endpoints.ANIME_DETAILS + "/$malId" + Endpoints.ANIME_RECOMMENDATIONS
      }
    }

    val res = safeCall.invokeWithRetry<ResponseDataListWrapper<AnimeRecommendationsResponse>, GeneralError>(client, request)

    return if (res is Resource.Success && res.data != null) {
      Resource.Success(
        res.data.data.map { AnimeVerticalDataModel.from(it) }
      )
    } else {
      Resource.Error(res.message)
    }
  }
}