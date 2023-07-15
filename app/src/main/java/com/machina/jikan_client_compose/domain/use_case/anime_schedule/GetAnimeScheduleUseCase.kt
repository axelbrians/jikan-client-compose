package com.machina.jikan_client_compose.domain.use_case.anime_schedule

import android.icu.util.Calendar
import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.anime.AnimeService
import com.machina.jikan_client_compose.data.remote.dto.anime_schedules.toAnimeSchedule
import com.machina.jikan_client_compose.domain.model.anime.AnimePortraitDataModel
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalModel
import com.machina.jikan_client_compose.presentation.data.StateListWrapper
import com.machina.jikan_client_compose.presentation.data.StateWrapper
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeHorizontalListContentState
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeScheduleState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAnimeScheduleUseCase @Inject constructor(
  private val repository: AnimeService,
  private val dispatchers: DispatchersProvider
) {

  operator fun invoke(
    dayInCalendar: Int = Calendar.getInstance().get(Calendar.DAY_OF_WEEK),
    page: Int = 1
  ): Flow<AnimeScheduleState> {
    return flow {
      emit(AnimeScheduleState.Loading)

      val state = when (val res = repository.getAnimeSchedule(dayInCalendar, page)) {
        is Resource.Success -> {
          val data = res.data?.data?.map {
            it.toAnimeSchedule()
          }.orEmpty()

          AnimeScheduleState(
            data = data,
            error = Event(res.message)
          )
        }
        is Resource.Error -> AnimeScheduleState(error = Event(res.message))
      }

      emit(state)
    }.flowOn(dispatchers.io)
  }

  fun getAsAnimeHorizontalList(
    dayInCalendar: Int = Calendar.getInstance().get(Calendar.DAY_OF_WEEK),
    page: Int = 1
  ): Flow<AnimeHorizontalListContentState> {
    return flow {
      emit(AnimeHorizontalListContentState.Loading)

      val state = when (val res = repository.getAnimeSchedule(dayInCalendar, page)) {
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

  fun getAsStateWrapper(
    dayInCalendar: Int = Calendar.getInstance().get(Calendar.DAY_OF_WEEK),
    page: Int = 1
  ): Flow<StateWrapper<AnimeVerticalModel>> = flow {
    emit(StateWrapper.loading())

    val state = when (val res = repository.getAnimeSchedule(dayInCalendar, page)) {
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

  fun getAsStateListWrapper(
    dayInCalendar: Int = Calendar.getInstance().get(Calendar.DAY_OF_WEEK),
    page: Int = 1
  ): Flow<StateListWrapper<AnimePortraitDataModel>> = flow {
    emit(StateListWrapper.loading())

    val state = when (val res = repository.getAnimeSchedule(dayInCalendar, page)) {
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
}