package com.machina.jikan_client_compose.data.remote

import com.machina.jikan_client_compose.core.Resource
import com.machina.jikan_client_compose.data.remote.dto.ContentSearchDtoKtor

interface MangaService {

  suspend fun searchManga(query: String, page: Int):
    Resource<List<ContentSearchDtoKtor>>
}