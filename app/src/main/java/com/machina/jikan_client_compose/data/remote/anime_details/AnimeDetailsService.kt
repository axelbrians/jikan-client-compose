package com.machina.jikan_client_compose.data.remote.anime_details

import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.domain.model.anime.AnimeCharacterModel
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalDataModel

interface AnimeDetailsService {
  suspend fun getAnimeCharacters(malId: Int): Resource<List<AnimeCharacterModel>>

  suspend fun getAnimeRecommendations(malId: Int): Resource<List<AnimeVerticalDataModel>>
}