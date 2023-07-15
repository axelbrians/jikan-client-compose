package com.machina.jikan_client_compose.domain.model.anime

import com.machina.jikan_client_compose.data.remote.dto.anime_characters.AnimeCharacterResponse
import com.machina.jikan_client_compose.data.remote.dto.anime_details.AnimeDetailsDto
import com.machina.jikan_client_compose.data.remote.dto.anime_minimal.AnimeMinimalDataResponse
import com.machina.jikan_client_compose.data.remote.dto.anime_recommendations.AnimeRecommendationResponse
import com.machina.jikan_client_compose.data.remote.dto.anime_schedules.AnimeScheduleDtoV4
import com.machina.jikan_client_compose.data.remote.dto.anime_top.AnimeTopDtoV4
import com.machina.jikan_client_compose.data.remote.dto.common.Jpg.Companion.getHighestResImgUrl

data class AnimePortraitDataModel(
	val malId: Int,
	val title: String,
	val score: Double,
	val imageUrl: String
) {
	companion object {

		fun from(data: AnimeDetailsDto): AnimePortraitDataModel {
			return AnimePortraitDataModel(
				malId = data.malId,
				title = data.title,
				score = data.score ?: 0.0,
				imageUrl = data.images.jpg.imageUrl
			)
		}

		fun from(data: AnimeScheduleDtoV4): AnimePortraitDataModel {
			return AnimePortraitDataModel(
				malId = data.malId,
				title = data.title,
				score = data.score ?: 0.0,
				imageUrl = data.images.jpg.imageUrl
			)
		}

		fun from(data: AnimeTopDtoV4): AnimePortraitDataModel {
			return AnimePortraitDataModel(
				malId = data.malId,
				title = data.title,
				score = data.score ?: 0.0,
				imageUrl = data.images.jpg.imageUrl
			)
		}

		fun from(data: AnimeTop): AnimePortraitDataModel {
			return AnimePortraitDataModel(
				malId = data.malId,
				title = data.title,
				score = data.score,
				imageUrl = data.imageUrl
			)
		}

		fun from(data: AnimeSchedule): AnimePortraitDataModel {
			return AnimePortraitDataModel(
				malId = data.malId,
				title = data.title,
				score = data.score,
				imageUrl = data.imageUrl
			)
		}

		fun from(data: AnimeRecommendationResponse): AnimePortraitDataModel {
			return AnimePortraitDataModel(
				malId = data.entry.malId,
				title = data.entry.title,
				score = 0.0,
				imageUrl = data.entry.images.jpg.getHighestResImgUrl()
			)
		}

		fun from(data: AnimeMinimalDataResponse): AnimePortraitDataModel {
			return AnimePortraitDataModel(
				malId = data.malId,
				title = data.title,
				score = 0.0,
				imageUrl = data.images.jpg.getHighestResImgUrl()
			)
		}

		fun from(data: AnimeCharacterResponse): AnimePortraitDataModel {
			return AnimePortraitDataModel(
				malId = data.character.malId,
				title = data.character.name,
				score = 0.0,
				imageUrl = data.character.images.jpg.getHighestResImgUrl()
			)
		}
	}
}