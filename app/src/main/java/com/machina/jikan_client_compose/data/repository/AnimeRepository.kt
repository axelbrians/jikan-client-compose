package com.machina.jikan_client_compose.data.repository

import com.machina.jikan_client_compose.core.SafeCall
import com.machina.jikan_client_compose.core.Endpoints
import com.machina.jikan_client_compose.core.error.GeneralError
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.AnimeService
import com.machina.jikan_client_compose.data.remote.dto.anime_schedule.AnimeScheduleDto
import com.machina.jikan_client_compose.data.remote.dto.anime_top.AnimeTopResponse
import com.machina.jikan_client_compose.data.remote.dto.content_details.ContentDetailsDto
import com.machina.jikan_client_compose.data.remote.dto.content_search.ContentSearchResponse
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_top.AnimeTopResponseV4
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.date.*
import javax.inject.Inject

class AnimeRepository @Inject constructor(
  private val client: HttpClient,
  private val safeCall: SafeCall
) : AnimeService {

  override suspend fun getTopAnimeList(page: Int): Resource<AnimeTopResponseV4> {
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

  override suspend fun getAnimeDetails(malId: Int): Resource<ContentDetailsDto> {
    val request = HttpRequestBuilder().apply {
      method = HttpMethod.Get
      url {
        protocol = URLProtocol.HTTPS
        host = Endpoints.HOST_V3
        encodedPath = Endpoints.ANIME_DETAILS + "/$malId"
      }
    }

    return safeCall<ContentDetailsDto, GeneralError>(client, request)
  }

  override suspend fun getAnimeSchedule(day: WeekDay): Resource<AnimeScheduleDto> {
    val dayInString = when (day) {
      WeekDay.MONDAY -> "monday"
      WeekDay.TUESDAY -> "tuesday"
      WeekDay.WEDNESDAY -> "wednesday"
      WeekDay.THURSDAY -> "thursday"
      WeekDay.FRIDAY -> "friday"
      WeekDay.SATURDAY -> "saturday"
      WeekDay.SUNDAY -> "sunday"
    }
    val request = HttpRequestBuilder().apply {
      method = HttpMethod.Get
      url {
        protocol = URLProtocol.HTTPS
        host = Endpoints.HOST_V3
        encodedPath = Endpoints.ANIME_SCHEDULE + "/$dayInString"
      }
    }

    return safeCall<AnimeScheduleDto, GeneralError>(client, request)
  }


}