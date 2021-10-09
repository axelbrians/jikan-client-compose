package com.machina.jikan_client_compose.data.response

import com.google.gson.annotations.SerializedName

data class ContentDetailsResponse(

  @SerializedName("request_hash")
  val requestHash: String,

  @SerializedName("request_cached")
  val requestCached: Boolean,

  @SerializedName("request_cache_expiry")
  val requestCacheExpiry: Int,

  @SerializedName("mal_id")
  val malId: Int,

  @SerializedName("url")
  val url: String,

  @SerializedName("title")
  val title: String,

  @SerializedName("title_english")
  val titleEnglish: String,

  @SerializedName("title_synonyms")
  val titleSynonyms: List<String>,

  @SerializedName("title_japanese")
  val titleJapanese: String,

  @SerializedName("status")
  val status: String,

  @SerializedName("image_url")
  val imageUrl: String,

  @SerializedName("type")
  val type: String,


  /*  Manga  */
  @SerializedName("publishing")
  val isPublishing: Boolean,

  @SerializedName("chapters")
  val chapters: Int,

  @SerializedName("volumes")
  val volumes: Int,

  /* - - - - - - - - - */


  /*  Anime  */
  @SerializedName("episodes")
  val episodeCount: Int,

  @SerializedName("airing")
  val isAiring: Boolean,

  @SerializedName("duration")
  val duration: Boolean,

  @SerializedName("rating")
  val ageRating: Boolean,

  @SerializedName("broadcast")
  val broadcast: String,

  /* - - - - - - - - - */

  @SerializedName("rank")
  val rank: Int,

  @SerializedName("score")
  val score: Double,

  @SerializedName("scored_by")
  val scoredBy: Int,

  @SerializedName("popularity")
  val popularity: Int,

  @SerializedName("members")
  val members: Int,

  @SerializedName("favorites")
  val favorites: Int,

  @SerializedName("synopsis")
  val synopsis: String,

  @SerializedName("background")
  val background: String,


)