package com.machina.jikan_client_compose.domain.repository

import com.machina.jikan_client_compose.data.network.Resource
import com.machina.jikan_client_compose.domain.model.AnimeTop
import com.machina.jikan_client_compose.domain.model.ContentSearch

interface AnimeRepository {

  suspend fun getTopAnimeList(): Resource<List<AnimeTop>>

  suspend fun searchAnime(query: String, page: Int): Resource<List<ContentSearch>>
}