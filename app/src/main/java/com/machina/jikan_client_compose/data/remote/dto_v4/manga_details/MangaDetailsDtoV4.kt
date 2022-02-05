package com.machina.jikan_client_compose.data.remote.dto_v4.manga_details

import com.machina.jikan_client_compose.data.remote.dto_v4.common.*
import com.machina.jikan_client_compose.data.remote.dto_v4.manga.Author
import com.machina.jikan_client_compose.data.remote.dto_v4.manga.Published
import com.machina.jikan_client_compose.data.remote.dto_v4.manga.Serialization
import com.machina.jikan_client_compose.domain.model.ContentDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MangaDetailsDtoV4(
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
  @SerialName("scored")
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

  /* Manga Specific */
  @SerialName("chapters")
  val chapters: Int = 0,
  @SerialName("volumes")
  val volumes: Int = 0,
  @SerialName("publishing")
  val publishing: Boolean = false,
  @SerialName("published")
  val published: Published = Published(),
  @SerialName("authors")
  val authors: List<Author> = listOf(),
  @SerialName("serializations")
  val serializations: List<Serialization> = listOf()
)

fun MangaDetailsDtoV4.toContentDetails(): ContentDetails {
  return ContentDetails(
    malId = malId,
    url = url,
    images = images,
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
    genres = genres,
    explicitGenres = explicitGenres,
    themes = themes,
    demographics = demographics,

    /* Manga Specific */
    chapters = chapters,
    volumes = volumes,
    isPublishing = publishing,
    published = published,
    authors = authors,
    serializations = serializations

  )
}