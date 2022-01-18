package com.machina.jikan_client_compose.domain.use_case.anime_schedule

import android.icu.util.Calendar
import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.dto.anime_schedule.AnimeScheduleDto
import com.machina.jikan_client_compose.data.remote.dto.anime_schedule.toAnimeSchedule
import com.machina.jikan_client_compose.data.remote.dto.anime_top.toAnimeTop
import com.machina.jikan_client_compose.data.repository.AnimeRepository
import com.machina.jikan_client_compose.domain.model.AnimeSchedule
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeScheduleState
import io.ktor.util.date.*
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

    val day = when(dayInCalendar) {
      Calendar.MONDAY -> WeekDay.MONDAY
      Calendar.TUESDAY -> WeekDay.TUESDAY
      Calendar.WEDNESDAY -> WeekDay.WEDNESDAY
      Calendar.THURSDAY -> WeekDay.THURSDAY
      Calendar.FRIDAY -> WeekDay.FRIDAY
      Calendar.SATURDAY -> WeekDay.SATURDAY
      Calendar.SUNDAY -> WeekDay.SUNDAY
      else -> WeekDay.MONDAY
    }

    return flow {
      emit(AnimeScheduleState(isLoading = true))

      val state = when (val res = repository.getAnimeSchedule(day)) {
        is Resource.Success -> {
          val data = resolveWhatDayScheduleToPick(day, res.data)
          AnimeScheduleState(data)
        }
        is Resource.Error -> AnimeScheduleState(error = Event(res.message))
        is Resource.Loading -> AnimeScheduleState(isLoading = true)
      }

      emit(state)
    }.flowOn(dispatchers.io)
  }

  private fun resolveWhatDayScheduleToPick(day: WeekDay, res: AnimeScheduleDto?): List<AnimeSchedule> {
    val schedules = when (day) {
      WeekDay.MONDAY -> res?.monday
      WeekDay.TUESDAY -> res?.tuesday
      WeekDay.WEDNESDAY -> res?.wednesday
      WeekDay.THURSDAY -> res?.thursday
      WeekDay.FRIDAY -> res?.friday
      WeekDay.SATURDAY -> res?.saturday
      WeekDay.SUNDAY -> res?.sunday
    }?.map { it.toAnimeSchedule() }


    return schedules ?: listOf()
  }
}