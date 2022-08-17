package com.machina.jikan_client_compose.data.remote.anime_details

import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_details.AnimeDetailsDtoV4
import com.machina.jikan_client_compose.domain.model.anime.AnimeCharacterModel
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalDataModel

interface AnimeDetailsService {

  suspend fun getAnimeDetails(malId: Int): Resource<AnimeDetailsDtoV4>

  suspend fun getAnimeCharacters(malId: Int): Resource<List<AnimeCharacterModel>>

  suspend fun getAnimeRecommendations(malId: Int): Resource<List<AnimeVerticalDataModel>>
}