package com.machina.jikan_client_compose.domain.use_case.get_top_anime

import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.error.EmptyDataException
import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.anime.AnimeService
import com.machina.jikan_client_compose.domain.model.anime.AnimePortraitDataModel
import com.machina.jikan_client_compose.domain.model.anime.AnimeThumbnail
import com.machina.jikan_client_compose.domain.use_case.anime.HomeSection
import com.machina.jikan_client_compose.domain.use_case.anime.SectionType
import com.machina.jikan_client_compose.presentation.data.StateListWrapper
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject

class GetAnimeTopUseCase @Inject constructor(
	private val service: AnimeService,
	private val dispatchers: DispatchersProvider,
	private val type: SectionType = SectionType.AnimeTop
) {
	suspend fun invoke(): List<AnimeThumbnail> {
		val resource = service.getAnimeAiringPopular()

		if (resource is Resource.Error) {
			throw Exception(resource.message)
		}

		val result = resource.data?.data?.map {
			AnimeThumbnail.from(it)
		}

		if (!result.isNullOrEmpty()) {
			return result
		} else {
			throw EmptyDataException()
		}
	}

	fun getAsStateListWrapper(page: Int = 1): Flow<StateListWrapper<AnimePortraitDataModel>> =
		flow {
			emit(StateListWrapper.loading())

			val state = when (val res = service.getAnimeTopOfAllTime(page)) {
				is Resource.Success -> {
					StateListWrapper(
						data = res.data!!.data.map {
							AnimePortraitDataModel.from(it)
						}
					)
				}

				is Resource.Error -> StateListWrapper(error = Event(res.message))
			}

			emit(state)
		}.flowOn(dispatchers.io)

	@Throws(Exception::class)
	suspend fun executeAsHomeSection(): HomeSection {
		val result = invoke().toImmutableList()
		return HomeSection(
			id = UUID.randomUUID().toString(),
			contents = result,
			type = type
		)
	}
}