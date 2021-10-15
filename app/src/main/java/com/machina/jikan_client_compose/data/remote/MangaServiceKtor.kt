package com.machina.jikan_client_compose.data.remote

import com.machina.jikan_client_compose.data.network.Resource
import com.machina.jikan_client_compose.data.remote.dto.ContentSearchDtoKtor

interface MangaServiceKtor {

  suspend fun searchManga(query: String, page: Int):
    Resource<List<ContentSearchDtoKtor>>
}