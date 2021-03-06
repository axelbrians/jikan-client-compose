package com.machina.jikan_client_compose.data.repository

import com.machina.jikan_client_compose.core.exception.MyError
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.AnimeService
import com.machina.jikan_client_compose.data.remote.dto.anime_top.AnimeTopDtoKtor
import com.machina.jikan_client_compose.data.remote.dto.anime_top.AnimeTopResponse
import com.machina.jikan_client_compose.data.remote.dto.content_details.ContentDetailsDto
import com.machina.jikan_client_compose.data.remote.dto.content_search.ContentSearchDtoKtor
import com.machina.jikan_client_compose.data.remote.dto.content_search.ContentSearchResponse
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_details.AnimeDetailsDtoV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_schedules.AnimeScheduleDtoV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_schedules.AnimeScheduleResponseV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_top.AnimeTopResponseV4
import com.machina.jikan_client_compose.domain.model.anime.AnimeTop
import io.ktor.util.date.*

class FakeAnimeRepository: AnimeService {

  private val topAnime = mutableListOf<AnimeTopDtoKtor>()

  var isReturnNetworkError = false


  override suspend fun getTopAnimeOfAllTime(page: Int): Resource<AnimeTopResponseV4> {
    return if (isReturnNetworkError) {
      Resource.Error(MyError.UNKNOWN_ERROR)
    } else {
      Resource.Success(AnimeTopResponseV4())
    }
  }

  override suspend fun searchAnime(query: String, page: Int): Resource<ContentSearchResponse> {
    return Resource.Error(MyError.UNKNOWN_ERROR)
  }

  override suspend fun getAnimeDetails(malId: Int): Resource<AnimeDetailsDtoV4> {
    return Resource.Error(MyError.UNKNOWN_ERROR)
  }

  override suspend fun getAnimeSchedule(day: Int): Resource<AnimeScheduleResponseV4> {
    return Resource.Error(MyError.UNKNOWN_ERROR)
  }
}