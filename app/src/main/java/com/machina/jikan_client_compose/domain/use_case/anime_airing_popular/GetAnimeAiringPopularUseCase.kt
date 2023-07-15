package com.machina.jikan_client_compose.domain.use_case.anime_airing_popular

import com.machina.jikan_client_compose.core.error.EmptyDataException
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.anime.AnimeService
import com.machina.jikan_client_compose.domain.model.anime.AnimeThumbnail

interface GetAnimeAiringPopularUseCase {
	suspend operator fun invoke(): List<AnimeThumbnail>
}

class GetAnimeAiringPopularUseCaseImpl(
	private val service: AnimeService
): GetAnimeAiringPopularUseCase {

	override suspend operator fun invoke(): List<AnimeThumbnail> {
		val resource = service.getAnimeAiringPopular()

		if (resource is Resource.Error) {
			throw Exception(resource.message)
		}

		val airingPopularList = resource.data?.data?.map {
			AnimeThumbnail.from(it)
		}

		if (!airingPopularList.isNullOrEmpty()) {
			return airingPopularList
		} else {
			throw EmptyDataException()
		}
	}
}
