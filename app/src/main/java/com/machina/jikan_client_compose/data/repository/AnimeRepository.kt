package com.machina.jikan_client_compose.data.repository

import com.machina.jikan_client_compose.core.SafeCall
import com.machina.jikan_client_compose.core.constant.AnimeConstant
import com.machina.jikan_client_compose.core.constant.Endpoints
import com.machina.jikan_client_compose.core.error.GeneralError
import com.machina.jikan_client_compose.core.extensions.defaultUrl
import com.machina.jikan_client_compose.core.helper.DateHelper
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.anime.AnimeService
import com.machina.jikan_client_compose.data.remote.dto.anime_airing_popular.AnimeAiringPopularResponseV4
import com.machina.jikan_client_compose.data.remote.dto.anime_schedules.AnimeScheduleResponseV4
import com.machina.jikan_client_compose.data.remote.dto.anime_top.AnimeTopResponseV4
import com.machina.jikan_client_compose.di.AndroidKtorClient
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeRepository @Inject constructor(
  @AndroidKtorClient private val client: HttpClient,
  private val safeCall: SafeCall
) : AnimeService {

  override suspend fun getAnimeTopOfAllTime(page: Int): Resource<AnimeTopResponseV4> {
    val request = HttpRequestBuilder().apply {
      defaultUrl { encodedPath = Endpoints.ANIME_TOP }
      parameter(AnimeConstant.PageKey, page)
    }

    return safeCall<AnimeTopResponseV4, GeneralError>(client, request, true)
  }

  override suspend fun getAnimeAiringPopular(): Resource<AnimeAiringPopularResponseV4> {
    val request = HttpRequestBuilder().apply {
      defaultUrl { encodedPath = Endpoints.ANIME_DETAILS }
      parameter(AnimeConstant.PageKey, 1)
      parameter(AnimeConstant.StatusKey, "airing")
      parameter(AnimeConstant.OrderByKey, "score")
      parameter(AnimeConstant.SortKey, "desc")
    }

    return safeCall<AnimeAiringPopularResponseV4, GeneralError>(client, request, true)
  }

  override suspend fun getAnimeSchedule(day: Int, page: Int): Resource<AnimeScheduleResponseV4> {
    val dayInString = DateHelper.parseDayIntToDayString(day)
    val request = HttpRequestBuilder().apply {
      defaultUrl {
        encodedPath = Endpoints.ANIME_SCHEDULES + "/$dayInString"
      }
      parameter(AnimeConstant.PageKey, page)
    }

    val res = safeCall<AnimeScheduleResponseV4, GeneralError>(client, request, true)

    // Sort the result by Rank, and move the 0 rank value to last
    return if (res is Resource.Success && res.data != null) {

      val sortedSchedule = res.data.data.sortedBy { it.rank }.toMutableList()
      val zeroRankCount = sortedSchedule.count { it.rank < 1 }

      for (i in 0 until zeroRankCount) {
        val temp = sortedSchedule.removeFirst()
        sortedSchedule.add(temp)
      }

      Resource.Success(res.data.copy(data = sortedSchedule))
    } else {
      res
    }
  }
}