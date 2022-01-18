package com.machina.jikan_client_compose.domain.model

import com.machina.jikan_client_compose.data.remote.dto.anime_schedule.*
import kotlinx.serialization.SerialName

data class AnimeSchedule(
  val malId: Int = 0,

  val url: String = "",

  val title: String = "",

  val imageUrl: String = "",

//  val synopsis: String = "",

//  val type: String? = null,

//  val airingStart: String = "",

  val episodes: Int? = null,

  val members: Int = 0,

//  val genres: List<Genre> = listOf(),

//  val explicitGenres: List<ExplicitGenre> = listOf(),

//  val themes: List<Theme> = listOf(),

  val demographics: List<Demographic> = listOf(),

//  val source: String = "",

//  val producers: List<Producer> = listOf(),

  val score: Double? = null,

//
//  val licensors: List<Any> = listOf(),

  val r18: Boolean = false,

  val kids: Boolean = false
)
