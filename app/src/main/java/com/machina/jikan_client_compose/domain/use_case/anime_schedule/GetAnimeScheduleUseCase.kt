package com.machina.jikan_client_compose.domain.use_case.anime_schedule

import android.icu.util.Calendar
import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_schedules.toAnimeSchedule
import com.machina.jikan_client_compose.data.repository.AnimeRepository
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeScheduleState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAnimeScheduleUseCase @Inject constructor(
  private val repository: AnimeRepository,
  private val dispatchers: DispatchersProvider
) {

  operator fun invoke(dayInCalendar: Int): Flow<AnimeScheduleState> {
    val calendar = Calendar.getInstance()
    calendar.get(Calendar.DAY_OF_WEEK)

    return flow {
      emit(AnimeScheduleState(isLoading = true))

      val state = when (val res = repository.getAnimeSchedule(dayInCalendar)) {
        is Resource.Success -> {
          val data = res.data?.data?.map {
            it.toAnimeSchedule()
          }?: emptyList()

          AnimeScheduleState(data)
        }
        is Resource.Error -> AnimeScheduleState(error = Event(res.message))
        is Resource.Loading -> AnimeScheduleState(isLoading = true)
      }

      emit(state)
    }.flowOn(dispatchers.io)
  }
}