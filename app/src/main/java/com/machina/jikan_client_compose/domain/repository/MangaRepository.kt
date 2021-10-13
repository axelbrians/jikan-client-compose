package com.machina.jikan_client_compose.domain.repository

import com.machina.jikan_client_compose.data.network.Resource
import com.machina.jikan_client_compose.domain.model.ContentSearch

interface MangaRepository {

  suspend fun searchManga(query: String, page: Int): Resource<List<ContentSearch>>
}