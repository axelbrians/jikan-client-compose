package com.machina.jikan_client_compose.data.repository

import android.icu.util.Calendar
import com.machina.jikan_client_compose.core.Endpoints
import com.machina.jikan_client_compose.core.SafeCall
import com.machina.jikan_client_compose.core.error.GeneralError
import com.machina.jikan_client_compose.core.exception.MyError
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.AnimeService
import com.machina.jikan_client_compose.data.remote.dto.content_search.ContentSearchResponse
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_airing_popular.AnimeAiringPopularResponseV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_characters.AnimeCharacterResponse
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_details.AnimeDetailsDtoV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_details.AnimeDetailsResponseV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_schedules.AnimeScheduleResponseV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_top.AnimeTopResponseV4
import com.machina.jikan_client_compose.data.remote.dto_v4.common.ResponseDataListWrapper
import com.machina.jikan_client_compose.di.AndroidKtorClient
import com.machina.jikan_client_compose.domain.model.anime.AnimeCharacterModel
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeRepository @Inject constructor(
  @AndroidKtorClient private val client: HttpClient,
  private val safeCall: SafeCall
) : AnimeService {

  override suspend fun getAnimeTopOfAllTime(page: Int): Resource<AnimeTopResponseV4> {
    val request = HttpRequestBuilder().apply {
      method = HttpMethod.Get
      url {
        protocol = URLProtocol.HTTPS
        host = Endpoints.HOST_V4
        encodedPath = Endpoints.ANIME_TOP
        parameter("page", page)
      }
    }

    return safeCall<AnimeTopResponseV4, GeneralError>(client, request)
  }

  override suspend fun getAnimeAiringPopular(): Resource<AnimeAiringPopularResponseV4> {
    val request = HttpRequestBuilder().apply {
      method = HttpMethod.Get
      url {
        protocol = URLProtocol.HTTPS
        host = Endpoints.HOST_V4
        encodedPath = Endpoints.ANIME_DETAILS
        parameter("page", 1)
        parameter("status", "airing")
        parameter("order_by", "score")
        parameter("sort", "desc")
      }
    }

    val res = safeCall<AnimeAiringPopularResponseV4, GeneralError>(client, request)

    Timber.d(res.data?.toString())
    Timber.d(res.message)
    return res
  }

  override suspend fun searchAnime(query: String, page: Int): Resource<ContentSearchResponse> {
    val request = HttpRequestBuilder().apply {
      method = HttpMethod.Get
      url {
        protocol = URLProtocol.HTTPS
        host = Endpoints.HOST_V3
        encodedPath = Endpoints.ANIME_SEARCH
        parameter("q", query)
        parameter("page", page)
      }
    }

    return safeCall<ContentSearchResponse, GeneralError>(client, request)
  }

  override suspend fun getAnimeSchedule(day: Int): Resource<AnimeScheduleResponseV4> {
    val dayInString = when (day) {
      Calendar.MONDAY -> "monday"
      Calendar.TUESDAY -> "tuesday"
      Calendar.WEDNESDAY -> "wednesday"
      Calendar.THURSDAY -> "thursday"
      Calendar.FRIDAY -> "friday"
      Calendar.SATURDAY -> "saturday"
      Calendar.SUNDAY -> "sunday"
      else -> "other"
    }
    val request = HttpRequestBuilder().apply {
      method = HttpMethod.Get
      url {
        protocol = URLProtocol.HTTPS
        host = Endpoints.HOST_V4
        encodedPath = Endpoints.ANIME_SCHEDULES + "/$dayInString"
      }
    }

    val res = safeCall<AnimeScheduleResponseV4, GeneralError>(client, request)

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

  override suspend fun getAnimeDetails(malId: Int): Resource<AnimeDetailsDtoV4> {
    val request = HttpRequestBuilder().apply {
      method = HttpMethod.Get
      url {
        protocol = URLProtocol.HTTPS
        host = Endpoints.HOST_V4
        encodedPath = Endpoints.ANIME_DETAILS + "/$malId"
      }
    }

    val res = safeCall<AnimeDetailsResponseV4, GeneralError>(client, request)

    return if (res is Resource.Success && res.data != null) {
      Resource.Success(res.data.data)
    } else {
      Resource.Error(res.message ?: MyError.UNKNOWN_ERROR)
    }
  }

  override suspend fun getAnimeCharacters(malId: Int): Resource<List<AnimeCharacterModel>> {
    val request = HttpRequestBuilder().apply {
      method = HttpMethod.Get
      url {
        protocol = URLProtocol.HTTPS
        host = Endpoints.HOST_V4
        encodedPath = Endpoints.ANIME_DETAILS + "/$malId" + Endpoints.ANIME_CHARACTERS
      }
    }

    val res = safeCall<ResponseDataListWrapper<AnimeCharacterResponse>, GeneralError>(client, request)

    Timber.tag("AnimeCharacter").d(res.message)
    return if (res is Resource.Success && res.data != null) {
      Resource.Success(
        res.data.data.map {
          AnimeCharacterModel.from(it)
        }
      )
    } else {
      Resource.Error(res.message ?: MyError.UNKNOWN_ERROR)
    }
  }
}