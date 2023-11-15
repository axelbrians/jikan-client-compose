package com.machina.jikan_client_compose.domain.use_case.anime

import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.domain.model.anime.AnimeThumbnail
import com.machina.jikan_client_compose.domain.use_case.anime_airing_popular.GetAnimeAiringPopularUseCase
import com.machina.jikan_client_compose.domain.use_case.anime_schedule.GetAnimeScheduleUseCase
import com.machina.jikan_client_compose.domain.use_case.get_top_anime.GetAnimeTopUseCase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

sealed class SectionType(val name: String) {
	object AnimeAiringPopular: SectionType("Airing Now")
	object AnimeSchedule: SectionType("New episodes today")
	object AnimeTop: SectionType("Top of all times")
}

data class HomeSection(
	val id: String,
	val contents: ImmutableList<AnimeThumbnail>,
	val type: SectionType
)

interface GetHomeContentUseCase {

	suspend fun invoke(): ImmutableList<HomeSection>

	fun onCleared()
}

class GetHomeContentUseCaseImpl(
	private val airingPopularUseCase: GetAnimeAiringPopularUseCase,
	private val animeScheduleUseCase: GetAnimeScheduleUseCase,
	private val animeTopUseCase: GetAnimeTopUseCase,
	private val dispatchers: DispatchersProvider
): GetHomeContentUseCase {

	private val supervisorJob = SupervisorJob()
	private val concurrentScope = CoroutineScope(dispatchers.io + supervisorJob)

	override suspend fun invoke(): ImmutableList<HomeSection> {
		val homeSections = mutableListOf<HomeSection>()
		val airingPopular = airingPopularUseCase.executeAsHomeSection()
		val airingToday = animeScheduleUseCase.executeAsHomeSection()
		val animeTop = animeTopUseCase.executeAsHomeSection()

		homeSections.add(airingPopular)
		homeSections.add(airingToday)
		homeSections.add(animeTop)
		return homeSections.toImmutableList()
	}

	override fun onCleared() {
		supervisorJob.cancel()
	}
}