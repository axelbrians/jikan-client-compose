package com.machina.jikan_client_compose.domain.model

import com.google.gson.annotations.SerializedName

data class ContentSearch(
  val malId: Int,
  val title: String,
  val url: String,
  val imageUrl: String,
  val synopsis: String,
  val type: String? = null,
  /* Optional based on ContentType */

  /* Anime */
  val isAiring: Boolean? = null,
  val rated: String? = null,
  val episodesCount: Int? = null,

  /*  Manga  */
  val isPublishing: Boolean? = null,
  val chapters: Int? = null,
  val volumes: Int? = null,

  /* - - - - - - - - - */

  val startDate: String?,
  val endDate: String?,
  val members: Int,
  val score: Double
)
