package com.machina.jikan_client_compose.data.remote.dto_v4.anime_top

import com.machina.jikan_client_compose.data.remote.dto_v4.anime.*
import com.machina.jikan_client_compose.data.remote.dto_v4.common.*
import com.machina.jikan_client_compose.domain.model.anime.AnimeTop
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeTopDtoV4(
  @SerialName("mal_id")
  val malId: Int = 0,
  @SerialName("url")
  val url: String = "",
  @SerialName("images")
  val images: Images = Images(),
  @SerialName("trailer")
  val trailer: Trailer = Trailer(),
  @SerialName("title")
  val title: String = "",
  @SerialName("title_english")
  val titleEnglish: String? = null,
  @SerialName("title_japanese")
  val titleJapanese: String? = null,
  @SerialName("title_synonyms")
  val titleSynonyms: List<String> = listOf(),
  @SerialName("type")
  val type: String = "",
  @SerialName("source")
  val source: String = "",
  @SerialName("episodes")
  val episodes: Int = 0,
  @SerialName("status")
  val status: String = "",
  @SerialName("airing")
  val airing: Boolean = false,
  @SerialName("aired")
  val aired: Aired = Aired(),
  @SerialName("duration")
  val duration: String = "",
  @SerialName("rating")
  val rating: String = "",
  @SerialName("score")
  val score: Double? = null,
  @SerialName("scored_by")
  val scoredBy: Int? = null,
  @SerialName("rank")
  val rank: Int = 0,
  @SerialName("popularity")
  val popularity: Int = 0,
  @SerialName("members")
  val members: Int = 0,
  @SerialName("favorites")
  val favorites: Int = 0,
  @SerialName("synopsis")
  val synopsis: String = "",
  @SerialName("background")
  val background: String? = null,
  @SerialName("season")
  val season: String? = null,
  @SerialName("year")
  val year: Int? = null,
  @SerialName("broadcast")
  val broadcast: Broadcast = Broadcast(),
  @SerialName("producers")
  val producers: List<Producer> = listOf(),
  @SerialName("licensors")
  val licensors: List<Licensor> = listOf(),
  @SerialName("studios")
  val studios: List<Studio> = listOf(),
  @SerialName("genres")
  val genres: List<Genre> = listOf(),
  @SerialName("explicit_genres")
  val explicitGenres: List<ExplicitGenre> = listOf(),
  @SerialName("themes")
  val themes: List<Theme> = listOf(),
  @SerialName("demographics")
  val demographics: List<Demographic> = listOf()
)

fun AnimeTopDtoV4.toAnimeTop(): AnimeTop {
  return AnimeTop(
    malId, rank, title,
    url, images.jpg.imageUrl, type,
    episodes,
    members, score ?: 0.0
  )
}