package com.machina.jikan_client_compose.domain.model.anime

import com.machina.jikan_client_compose.data.remote.dto_v4.anime_recommendations.AnimeRecommendationsResponse
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_schedules.AnimeScheduleDtoV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_top.AnimeTopDtoV4
import com.machina.jikan_client_compose.data.remote.dto_v4.common.Jpg.Companion.getValidLargestImgUrl

data class AnimeVerticalDataModel(
  val malId: Int,
  val title: String,
  val score: Double,
  val imageUrl: String
) {
  companion object {

    fun from(data: AnimeScheduleDtoV4): AnimeVerticalDataModel {
      return AnimeVerticalDataModel(
        malId = data.malId,
        title = data.title,
        score = data.score ?: 0.0,
        imageUrl = data.images.jpg.imageUrl
      )
    }

    fun from(data: AnimeTopDtoV4): AnimeVerticalDataModel {
      return AnimeVerticalDataModel(
        malId = data.malId,
        title = data.title,
        score = data.score ?: 0.0,
        imageUrl = data.images.jpg.imageUrl
      )
    }

    fun from(data: AnimeTop): AnimeVerticalDataModel {
      return AnimeVerticalDataModel(
        malId = data.malId,
        title = data.title,
        score = data.score,
        imageUrl = data.imageUrl
      )
    }

    fun from(data: AnimeSchedule): AnimeVerticalDataModel {
      return AnimeVerticalDataModel(
        malId = data.malId,
        title = data.title,
        score = data.score,
        imageUrl = data.imageUrl
      )
    }

    fun from(data: AnimeRecommendationsResponse): AnimeVerticalDataModel {
      return AnimeVerticalDataModel(
        malId = data.entry.malId,
        title = data.entry.title,
        score = 0.0,
        imageUrl = data.entry.images.jpg.getValidLargestImgUrl()
      )
    }
  }
}