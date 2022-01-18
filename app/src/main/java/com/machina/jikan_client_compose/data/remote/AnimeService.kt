package com.machina.jikan_client_compose.data.remote

import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.dto.*

interface AnimeService {

  suspend fun getTopAnimeList(page: Int):
    Resource<AnimeTopResponse>

  suspend fun searchAnime(query: String, page: Int):
    Resource<ContentSearchResponse>

  suspend fun getAnimeDetails(malId: Int):
    Resource<ContentDetailsDto>
}