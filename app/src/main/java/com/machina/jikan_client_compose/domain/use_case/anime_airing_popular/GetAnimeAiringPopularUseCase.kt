package com.machina.jikan_client_compose.domain.use_case.anime_airing_popular

import com.machina.jikan_client_compose.core.error.EmptyDataException
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.anime.AnimeService
import com.machina.jikan_client_compose.domain.model.anime.AnimeThumbnail
import com.machina.jikan_client_compose.domain.use_case.anime.HomeSection
import com.machina.jikan_client_compose.domain.use_case.anime.SectionType
import java.util.UUID
import kotlin.jvm.Throws

interface GetAnimeAiringPopularUseCase {
	@Throws(Exception::class)
	suspend operator fun invoke(): List<AnimeThumbnail>

	@Throws(Exception::class)
	suspend fun executeAsHomeSection(): HomeSection
}

class GetAnimeAiringPopularUseCaseImpl(
	private val service: AnimeService,
	private val type: SectionType = SectionType.AnimeAiringPopular
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

	override suspend fun executeAsHomeSection(): HomeSection {
		val result = invoke()
		return HomeSection(
			id = UUID.randomUUID().toString(),
			contents = result,
			type = type
		)
	}
}
