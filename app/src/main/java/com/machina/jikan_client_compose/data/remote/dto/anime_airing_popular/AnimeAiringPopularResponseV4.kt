package com.machina.jikan_client_compose.data.remote.dto.anime_airing_popular

import com.machina.jikan_client_compose.data.remote.dto.anime_common.AnimeGeneralDtoV4
import com.machina.jikan_client_compose.data.remote.dto.anime_common.Pagination
import com.machina.jikan_client_compose.data.remote.dto.common.Jpg.Companion.getHighestResImgUrl
import com.machina.jikan_client_compose.domain.model.anime.AnimeAiringPopular
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeAiringPopularResponseV4(
  @SerialName("data")
  val data: List<AnimeGeneralDtoV4> = listOf(),
  @SerialName("pagination")
  val pagination: Pagination = Pagination()
)

fun AnimeGeneralDtoV4.toAnimeAiringPopular(): AnimeAiringPopular {
  return AnimeAiringPopular(
    malId, rank, title,
    url, images.jpg.getHighestResImgUrl(), type,
    episodes,
    members, score ?: 0.0
  )
}

