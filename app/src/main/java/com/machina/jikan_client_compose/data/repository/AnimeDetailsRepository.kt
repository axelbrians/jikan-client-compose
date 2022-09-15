package com.machina.jikan_client_compose.data.repository

import com.machina.jikan_client_compose.core.SafeCall
import com.machina.jikan_client_compose.core.constant.Endpoints
import com.machina.jikan_client_compose.core.error.GeneralError
import com.machina.jikan_client_compose.core.extensions.defaultUrl
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.core.wrapper.ResponseDataListWrapper
import com.machina.jikan_client_compose.data.remote.anime_details.AnimeDetailsService
import com.machina.jikan_client_compose.data.remote.dto.anime_characters.AnimeCharacterResponse
import com.machina.jikan_client_compose.data.remote.dto.anime_details.AnimeDetailsDto
import com.machina.jikan_client_compose.data.remote.dto.anime_details.AnimeDetailsResponseV4
import com.machina.jikan_client_compose.data.remote.dto.anime_recommendations.AnimeRecommendationResponse
import com.machina.jikan_client_compose.di.AndroidKtorClient
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalDataModel
import io.ktor.client.*
import io.ktor.client.request.*

class AnimeDetailsRepository(
  @AndroidKtorClient private val client: HttpClient,
  private val safeCall: SafeCall
): AnimeDetailsService {

  override suspend fun getAnimeDetails(malId: Int): Resource<AnimeDetailsDto> {
    val request = HttpRequestBuilder().apply {
      defaultUrl { encodedPath = Endpoints.ANIME_DETAILS + "/$malId" }
    }

    val res = safeCall<AnimeDetailsResponseV4, GeneralError>(
      client, request, true
    )
    return if (res is Resource.Success && res.data != null) {
      Resource.Success(res.data.data)
    } else {
      Resource.Error(res.message)
    }
  }

  override suspend fun getAnimeCharacters(malId: Int): Resource<List<AnimeCharacterResponse>> {
    val request = HttpRequestBuilder().apply {
      defaultUrl {
        encodedPath = Endpoints.getAnimeCharactersEndpoint(malId)
      }
    }

    val res = safeCall<ResponseDataListWrapper<AnimeCharacterResponse>, GeneralError>(
      client, request, true
    )
    return if (res is Resource.Success && res.data != null) {
      val orderedCharacters = res.data.data.sortedByDescending { it.favoritesCount }
      Resource.Success(orderedCharacters)
    } else {
      Resource.Error(res.message)
    }
  }

  override suspend fun getAnimeRecommendations(malId: Int): Resource<List<AnimeVerticalDataModel>> {
    val request = HttpRequestBuilder().apply {
      defaultUrl { encodedPath = Endpoints.getAnimeRecommendationEndpoint(malId) }
    }

    val res = safeCall<ResponseDataListWrapper<AnimeRecommendationResponse>, GeneralError>(
      client, request, true
    )

    return if (res is Resource.Success && res.data != null) {
      Resource.Success(
        res.data.data.map { AnimeVerticalDataModel.from(it) }
      )
    } else {
      Resource.Error(res.message)
    }
  }
}