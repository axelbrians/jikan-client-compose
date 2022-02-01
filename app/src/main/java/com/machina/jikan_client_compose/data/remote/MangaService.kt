package com.machina.jikan_client_compose.data.remote

import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.dto.content_details.ContentDetailsDto
import com.machina.jikan_client_compose.data.remote.dto.content_search.ContentSearchResponse
import com.machina.jikan_client_compose.data.remote.dto_v4.manga_details.MangaDetailsDtoV4
import com.machina.jikan_client_compose.data.remote.dto_v4.manga_details.MangaDetailsResponseV4

interface MangaService {

  suspend fun searchManga(query: String, page: Int):
    Resource<ContentSearchResponse>

  suspend fun getMangaDetails(malId: Int):
    Resource<MangaDetailsDtoV4>
}