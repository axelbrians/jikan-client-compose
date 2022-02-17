package com.machina.jikan_client_compose.data.remote.dto_v4.anime_airing_popular

import com.machina.jikan_client_compose.data.remote.dto_v4.anime.AnimeGeneralDtoV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime.Pagination
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_top.AnimeTopDtoV4
import com.machina.jikan_client_compose.domain.model.anime.AnimeAiringPopular
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeAiringPopularResponseV4(
  @SerialName("data")
  val `data`: List<AnimeGeneralDtoV4> = listOf(),
  @SerialName("pagination")
  val pagination: Pagination = Pagination()
)

fun AnimeGeneralDtoV4.toAnimeAiringPopular(): AnimeAiringPopular {
  return AnimeAiringPopular(
    malId, rank, title,
    url, images.jpg.imageUrl, type,
    episodes,
    members, score ?: 0.0
  )
}

