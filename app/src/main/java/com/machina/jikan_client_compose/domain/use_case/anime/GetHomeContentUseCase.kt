package com.machina.jikan_client_compose.domain.use_case.anime

import com.machina.jikan_client_compose.core.DefaultDispatchers
import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.error.EmptyDataException
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.anime.AnimeService
import com.machina.jikan_client_compose.domain.model.anime.AnimeThumbnail
import com.machina.jikan_client_compose.domain.use_case.anime_airing_popular.GetAnimeAiringPopularUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import java.lang.Exception

sealed class SectionType(val name: String) {
	object AnimeAiringPopular: SectionType("Airing Now")
	object AnimeSchedule: SectionType("New episodes today")
	object AnimeTop: SectionType("Top of all times")
}

data class HomeSection(
	val id: String,
	val contents: List<AnimeThumbnail>,
	val type: SectionType
)

interface GetHomeContentUseCase {

	operator fun invoke(): Flow<List<HomeSection>>

	fun onCleared()
}

class GetHomeContentUseCaseImpl(
	private val airingPopularUseCase: GetAnimeAiringPopularUseCase,
	private val dispatchers: DispatchersProvider
): GetHomeContentUseCase {

	private val supervisorJob = SupervisorJob()
	private val concurrentScope = CoroutineScope(dispatchers.io + supervisorJob)

	override fun invoke(): Flow<List<HomeSection>> = flow {
		val homeSections = mutableListOf<HomeSection>()
		val result = airingPopularUseCase.executeAsHomeSection()

		homeSections.add(result)
		emit(homeSections)
	}.flowOn(dispatchers.io)

	override fun onCleared() {
		supervisorJob.cancel()
	}
}