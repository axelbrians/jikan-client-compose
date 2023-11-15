package com.machina.jikan_client_compose.domain.use_case.anime_schedule

import android.icu.util.Calendar
import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.error.EmptyDataException
import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.anime.AnimeService
import com.machina.jikan_client_compose.domain.model.anime.AnimePortraitDataModel
import com.machina.jikan_client_compose.domain.model.anime.AnimeThumbnail
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalModel
import com.machina.jikan_client_compose.domain.use_case.anime.HomeSection
import com.machina.jikan_client_compose.domain.use_case.anime.SectionType
import com.machina.jikan_client_compose.presentation.data.StateListWrapper
import com.machina.jikan_client_compose.presentation.data.StateWrapper
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeHorizontalListContentState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.UUID

interface GetAnimeScheduleUseCase {
	suspend operator fun invoke(
		dayInCalendar: Int = Calendar.getInstance().get(Calendar.DAY_OF_WEEK),
		page: Int = 1
	): List<AnimeThumbnail>

	fun getAsAnimeHorizontalList(
		dayInCalendar: Int = Calendar.getInstance().get(Calendar.DAY_OF_WEEK),
		page: Int = 1
	): Flow<AnimeHorizontalListContentState>

	fun getAsStateWrapper(
		dayInCalendar: Int = Calendar.getInstance().get(Calendar.DAY_OF_WEEK),
		page: Int = 1
	): Flow<StateWrapper<AnimeVerticalModel>>

	fun getAsStateListWrapper(
		dayInCalendar: Int = Calendar.getInstance().get(Calendar.DAY_OF_WEEK),
		page: Int = 1
	): Flow<StateListWrapper<AnimePortraitDataModel>>

	suspend fun executeAsHomeSection(
		dayInCalendar: Int = Calendar.getInstance().get(Calendar.DAY_OF_WEEK),
		page: Int = 1
	): HomeSection
}

class GetAnimeScheduleUseCaseImpl(
	private val animeService: AnimeService,
	private val dispatchers: DispatchersProvider,
	private val type: SectionType = SectionType.AnimeSchedule
): GetAnimeScheduleUseCase {

	override suspend operator fun invoke(
		dayInCalendar: Int,
		page: Int
	): List<AnimeThumbnail> {
		val resource = animeService.getAnimeSchedule(dayInCalendar, page)

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

	override fun getAsAnimeHorizontalList(
		dayInCalendar: Int,
		page: Int
	): Flow<AnimeHorizontalListContentState> {
		return flow {
			emit(AnimeHorizontalListContentState.Loading)

			val state = when (val res = animeService.getAnimeSchedule(dayInCalendar, page)) {
				is Resource.Success -> {
					AnimeHorizontalListContentState(
						data = AnimeVerticalModel(
							data = res.data!!.data.map {
								AnimePortraitDataModel.from(it)
							},
							pagination = res.data.pagination
						)
					)
				}
				is Resource.Error -> AnimeHorizontalListContentState(error = Event(res.message))
			}

			emit(state)
		}.flowOn(dispatchers.io)
	}

	override fun getAsStateWrapper(
		dayInCalendar: Int,
		page: Int
	): Flow<StateWrapper<AnimeVerticalModel>> = flow {
		emit(StateWrapper.loading())

		val state = when (val res = animeService.getAnimeSchedule(dayInCalendar, page)) {
			is Resource.Success -> {
				StateWrapper(
					data = AnimeVerticalModel(
						data = res.data!!.data.map {
							AnimePortraitDataModel.from(it)
						},
						pagination = res.data.pagination
					)
				)
			}
			is Resource.Error -> StateWrapper(error = Event(res.message))
		}

		emit(state)
	}.flowOn(dispatchers.io)

	override fun getAsStateListWrapper(
		dayInCalendar: Int,
		page: Int
	): Flow<StateListWrapper<AnimePortraitDataModel>> = flow {
		emit(StateListWrapper.loading())

		val state = when (val res = animeService.getAnimeSchedule(dayInCalendar, page)) {
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

	override suspend fun executeAsHomeSection(
		dayInCalendar: Int,
		page: Int
	): HomeSection {
		val result = invoke(dayInCalendar, page)
		return HomeSection(
			id = UUID.randomUUID().toString(),
			contents = result,
			type = type
		)
	}
}