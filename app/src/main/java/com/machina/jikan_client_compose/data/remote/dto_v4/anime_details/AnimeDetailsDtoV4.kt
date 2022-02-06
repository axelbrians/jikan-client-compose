package com.machina.jikan_client_compose.data.remote.dto_v4.anime_details


import com.machina.jikan_client_compose.data.remote.dto_v4.anime.*
import com.machina.jikan_client_compose.data.remote.dto_v4.common.*
import com.machina.jikan_client_compose.domain.model.ContentDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeDetailsDtoV4(
  @SerialName("mal_id")
  val malId: Int = 0,
  @SerialName("url")
  val url: String = "",
  @SerialName("images")
  val images: Images = Images(),
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
  @SerialName("status")
  val status: String = "",
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
  @SerialName("genres")
  val genres: List<Genre> = listOf(),
  @SerialName("explicit_genres")
  val explicitGenres: List<ExplicitGenre> = listOf(),
  @SerialName("themes")
  val themes: List<Theme> = listOf(),
  @SerialName("demographics")
  val demographics: List<Demographic> = listOf(),


  @SerialName("trailer")
  val trailer: Trailer = Trailer(),
  @SerialName("source")
  val source: String = "",
  @SerialName("episodes")
  val episodes: Int = 0,
  @SerialName("airing")
  val airing: Boolean = false,
  @SerialName("aired")
  val aired: Aired = Aired(),
  @SerialName("duration")
  val duration: String = "",
  @SerialName("rating")
  val ageRating: String = "",
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
)

fun AnimeDetailsDtoV4.toContentDetails(): ContentDetails {

  val combinedGenres = mutableListOf<Genre>()

  combinedGenres.apply {
    addAll(genres)
    addAll(explicitGenres.map { it.toGenre() })
    addAll(themes.map { it.toGenre() })
    addAll(demographics.map { it.toGenre() })
    if (ageRating.isBlank()) {
      add(Genre(name = "Content rating: $ageRating"))
    }
  }

  return ContentDetails(
    malId = malId,
    url = url,
    title = title,
    titleEnglish = titleEnglish.orEmpty(),
    titleJapanese = titleJapanese.orEmpty(),
    titleSynonyms = titleSynonyms,
    type = type,
    status = status,
    score = score ?: 0.0,
    scoredBy = scoredBy ?: 0,
    rank = rank,
    popularity = popularity,
    members = members,
    favorites = favorites,
    synopsis = synopsis,
    background = background,
    genres = combinedGenres,
    images = images,
    explicitGenres = explicitGenres,
    themes = themes,
    demographics = demographics,

    /* Anime Specific */
    trailer = trailer,
    source = source,
    episodes = episodes,
    isAiring = airing,
    duration = duration,
    ageRating = ageRating,
    season = season,
    year = year,
    broadcast = broadcast,
    producers = producers,
    licensors = licensors,
    studios = studios
  )
}