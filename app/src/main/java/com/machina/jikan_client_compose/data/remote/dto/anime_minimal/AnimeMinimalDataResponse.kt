package com.machina.jikan_client_compose.data.remote.dto.anime_minimal

import com.machina.jikan_client_compose.data.remote.dto.anime_characters.AnimeCharacterResponse
import com.machina.jikan_client_compose.data.remote.dto.anime_details.AnimeDetailsDto
import com.machina.jikan_client_compose.data.remote.dto.anime_recommendations.AnimeRecommendationResponse
import com.machina.jikan_client_compose.data.remote.dto.common.Images
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeMinimalDataResponse(
	@SerialName("mal_id")
	val malId: Int = 0,
	@SerialName("title")
	val title: String = "",
	@SerialName("url")
	val url: String = "",
	@SerialName("images")
	val images: Images = Images(),
) {
	companion object {
		fun from(data: AnimeDetailsDto): AnimeMinimalDataResponse {
			return AnimeMinimalDataResponse(
				malId = data.malId,
				title = data.title,
				url = data.url,
				images = data.images
			)
		}

		fun from(data: AnimeCharacterResponse): AnimeMinimalDataResponse {
			return AnimeMinimalDataResponse(
				malId = data.character.malId,
				title = data.character.name,
				url = data.character.url,
				images = data.character.images
			)
		}

		fun from(data: AnimeRecommendationResponse): AnimeMinimalDataResponse {
			return AnimeMinimalDataResponse(
				malId = data.entry.malId,
				title = data.entry.title,
				url = data.entry.url,
				images = data.entry.images
			)
		}
	}
}
