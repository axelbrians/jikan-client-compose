package com.machina.jikan_client_compose.data.remote

import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.dto.ContentDetailsDto
import com.machina.jikan_client_compose.data.remote.dto.ContentSearchDtoKtor
import com.machina.jikan_client_compose.data.remote.dto.ContentSearchResponse
import com.machina.jikan_client_compose.domain.model.ContentDetails

interface MangaService {

  suspend fun searchManga(query: String, page: Int):
    Resource<ContentSearchResponse>

  suspend fun getMangaDetails(malId: Int):
    Resource<ContentDetailsDto>
}