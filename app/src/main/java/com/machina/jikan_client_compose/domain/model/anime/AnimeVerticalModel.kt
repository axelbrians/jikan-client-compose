package com.machina.jikan_client_compose.domain.model.anime

data class AnimeVerticalModel(
  val malId: Int,
  val title: String,
  val score: Double,
  val imageUrl: String
) {
  companion object {

    fun from(data: AnimeTop): AnimeVerticalModel {
      return AnimeVerticalModel(
        malId = data.malId,
        title = data.title,
        score = data.score,
        imageUrl = data.imageUrl
      )
    }

    fun from(data: AnimeSchedule): AnimeVerticalModel {
      return AnimeVerticalModel(
        malId = data.malId,
        title = data.title,
        score = data.score,
        imageUrl = data.imageUrl
      )
    }
  }
}