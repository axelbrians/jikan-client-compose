package com.machina.jikan_client_compose.data.remote.anime_details

import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.dto.anime_characters.AnimeCharacterResponse
import com.machina.jikan_client_compose.data.remote.dto.anime_details.AnimeDetailsDto
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalDataModel

interface AnimeDetailsService {

  suspend fun getAnimeDetails(malId: Int): Resource<AnimeDetailsDto>

  suspend fun getAnimeCharacters(malId: Int): Resource<List<AnimeCharacterResponse>>

  suspend fun getAnimeRecommendations(malId: Int): Resource<List<AnimeVerticalDataModel>>
}