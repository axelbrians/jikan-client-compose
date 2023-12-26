package com.machina.jikan_client_compose.domain.use_case.anime

import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.domain.model.anime.AnimeThumbnail
import com.machina.jikan_client_compose.domain.use_case.anime_airing_popular.GetAnimeAiringPopularUseCase
import com.machina.jikan_client_compose.domain.use_case.anime_schedule.GetAnimeScheduleUseCase
import com.machina.jikan_client_compose.domain.use_case.get_top_anime.GetAnimeTopUseCase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.util.UUID

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

interface GetHomeSectionsUseCase {

	suspend fun invoke(): ImmutableList<HomeSection>

	fun onCleared()
}

class GetHomeSectionsUseCaseImpl(
	private val airingPopularUseCase: GetAnimeAiringPopularUseCase,
	private val animeScheduleUseCase: GetAnimeScheduleUseCase,
	private val animeTopUseCase: GetAnimeTopUseCase,
	private val dispatchers: DispatchersProvider
): GetHomeSectionsUseCase {

	private val supervisorJob = SupervisorJob()
	private val concurrentScope = CoroutineScope(dispatchers.io + supervisorJob)

	override suspend fun invoke(): ImmutableList<HomeSection> {
		val airingPopular = airingPopularUseCase.executeAsHomeSection()
		val airingToday = animeScheduleUseCase.executeAsHomeSection()
		val animeTop = animeTopUseCase.executeAsHomeSection()

		// TODO: revert to non shuffled
		return buildList {
			add(airingPopular)
			add(airingToday)
			add(animeTop)
			add(airingPopular.copy(id = UUID.randomUUID().toString()))
			add(airingToday.copy(id = UUID.randomUUID().toString()))
			add(animeTop.copy(id = UUID.randomUUID().toString()))
		}.shuffled().toImmutableList()
	}

	override fun onCleared() {
		supervisorJob.cancel()
	}
}