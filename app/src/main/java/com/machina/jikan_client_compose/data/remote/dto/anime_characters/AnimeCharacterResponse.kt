package com.machina.jikan_client_compose.data.remote.dto.anime_characters

import com.machina.jikan_client_compose.data.remote.dto.common.Character
import com.machina.jikan_client_compose.data.remote.dto.common.Person
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeCharacterResponse(
  @SerialName("character")
  val character: Character = Character(),
  @SerialName("role")
  val role: String = "",
  @SerialName("favorites")
  val favoritesCount: Int = 0,
  @SerialName("voice_actors")
  val voiceActors: List<Person> = listOf(),
)
