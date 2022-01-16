package com.machina.jikan_client_compose.data.remote

import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.dto.AnimeTopDtoKtor
import com.machina.jikan_client_compose.data.remote.dto.ContentDetailsDto
import com.machina.jikan_client_compose.data.remote.dto.ContentSearchDtoKtor

interface AnimeService {

  suspend fun getTopAnimeList(page: Int):
    Resource<List<AnimeTopDtoKtor>>

  suspend fun searchAnime(query: String, page: Int):
    Resource<List<ContentSearchDtoKtor>>

  suspend fun getAnimeDetails(malId: Int):
    Resource<ContentDetailsDto>
}