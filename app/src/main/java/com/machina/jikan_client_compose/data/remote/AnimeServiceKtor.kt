package com.machina.jikan_client_compose.data.remote

import com.machina.jikan_client_compose.data.network.Resource
import com.machina.jikan_client_compose.data.remote.dto.AnimeTopDtoKtor
import com.machina.jikan_client_compose.data.remote.dto.ContentSearchDtoKtor
import com.machina.jikan_client_compose.domain.model.ContentSearch

interface AnimeServiceKtor {

  suspend fun getTopAnimeList():
    Resource<List<AnimeTopDtoKtor>>

  suspend fun searchAnime(query: String, page: Int):
    Resource<List<ContentSearchDtoKtor>>
}