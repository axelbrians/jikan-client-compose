package com.machina.jikan_client_compose.domain.model.anime

import com.machina.jikan_client_compose.data.remote.dto_v4.anime_characters.AnimeCharacterResponse
import com.machina.jikan_client_compose.data.remote.dto_v4.common.Jpg.Companion.getHighestResImgUrl

data class AnimeCharacterModel(
  val malId: Int,
  val name: String,
  val imageUrl: String,
  val role: String,
  val url: String
) {
  companion object {
    fun from(
      animeCharacter: AnimeCharacterResponse
    ): AnimeCharacterModel {
      return AnimeCharacterModel(
        malId = animeCharacter.character.malId,
        name = animeCharacter.character.name,
        imageUrl = animeCharacter.character.images.jpg.getHighestResImgUrl(),
        role = animeCharacter.role,
        url = animeCharacter.character.url
      )
    }
  }
}