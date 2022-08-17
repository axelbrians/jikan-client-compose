package com.machina.jikan_client_compose.data.repository

import com.machina.jikan_client_compose.core.error.MyError
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.AnimeService
import com.machina.jikan_client_compose.data.remote.dto.anime_top.AnimeTopDtoKtor
import com.machina.jikan_client_compose.data.remote.dto.content_search.ContentSearchResponse
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_airing_popular.AnimeAiringPopularResponseV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_details.AnimeDetailsDtoV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_schedules.AnimeScheduleResponseV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_top.AnimeTopResponseV4
import com.machina.jikan_client_compose.domain.model.anime.AnimeCharacterModel

class FakeAnimeRepository: AnimeService {

  private val topAnime = mutableListOf<AnimeTopDtoKtor>()

  var isReturnNetworkError = false


  override suspend fun getAnimeTopOfAllTime(page: Int): Resource<AnimeTopResponseV4> {
    return if (isReturnNetworkError) {
      Resource.Error(MyError.UNKNOWN_ERROR)
    } else {
      Resource.Success(AnimeTopResponseV4())
    }
  }

  override suspend fun getAnimeAiringPopular(): Resource<AnimeAiringPopularResponseV4> {
    TODO("Not yet implemented")
  }

  override suspend fun searchAnime(query: String, page: Int): Resource<ContentSearchResponse> {
    return Resource.Error(MyError.UNKNOWN_ERROR)
  }

  override suspend fun getAnimeDetails(malId: Int): Resource<AnimeDetailsDtoV4> {
    return Resource.Error(MyError.UNKNOWN_ERROR)
  }

  override suspend fun getAnimeCharacters(malId: Int): Resource<List<AnimeCharacterModel>> {
    TODO("Not yet implemented")
  }

  override suspend fun getAnimeSchedule(day: Int): Resource<AnimeScheduleResponseV4> {
    return Resource.Error(MyError.UNKNOWN_ERROR)
  }
}