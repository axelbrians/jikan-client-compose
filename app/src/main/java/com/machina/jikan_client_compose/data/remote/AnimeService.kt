package com.machina.jikan_client_compose.data.remote

import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.dto.content_search.ContentSearchResponse
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_airing_popular.AnimeAiringPopularResponseV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_details.AnimeDetailsDtoV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_schedules.AnimeScheduleResponseV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_top.AnimeTopResponseV4
import io.ktor.util.date.*

interface AnimeService {

  suspend fun getTopAnimeOfAllTime(page: Int):
    Resource<AnimeTopResponseV4>

  suspend fun getAiringPopularAnime():
    Resource<AnimeAiringPopularResponseV4>

  suspend fun searchAnime(query: String, page: Int):
    Resource<ContentSearchResponse>

  suspend fun getAnimeDetails(malId: Int):
    Resource<AnimeDetailsDtoV4>

  suspend fun getAnimeSchedule(day: Int):
    Resource<AnimeScheduleResponseV4>

}