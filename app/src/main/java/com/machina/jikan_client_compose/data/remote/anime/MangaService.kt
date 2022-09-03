package com.machina.jikan_client_compose.data.remote.anime

import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.dto.manga_details.MangaDetailsDtoV4

interface MangaService {

  suspend fun getMangaDetails(malId: Int):
    Resource<MangaDetailsDtoV4>
}