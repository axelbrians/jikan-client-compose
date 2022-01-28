package com.machina.jikan_client_compose.presentation.home_screen.data

import android.icu.util.Calendar
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.jikan_client_compose.core.enum.ContentType
import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.domain.use_case.anime_schedule.GetAnimeScheduleUseCase
import com.machina.jikan_client_compose.domain.use_case.get_top_anime.GetTopAnimeUseCase
import com.machina.jikan_client_compose.domain.use_case.search_content.SearchContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val searchContentUseCase: SearchContentUseCase,
  private val getTopAnimeUseCase: GetTopAnimeUseCase,
  private val getAnimeScheduleUseCase: GetAnimeScheduleUseCase
) : ViewModel() {

  private val _animeTopState: MutableState<AnimeTopState> = mutableStateOf(AnimeTopState())
  val animeTopState: State<AnimeTopState> = _animeTopState

  private val _animeScheduleState : MutableState<AnimeScheduleState> =
    mutableStateOf(AnimeScheduleState())
  val animeScheduleState : State<AnimeScheduleState> = _animeScheduleState


  private val _contentSearchState: MutableState<ContentSearchState> =
    mutableStateOf(ContentSearchState())
  val contentSearchState: State<ContentSearchState> = _contentSearchState

  private var currentPage = 1

  fun getTopAnimeList() {
    getTopAnimeUseCase().onEach {
      Timber.d(it.error.peekContent())
      _animeTopState.value = it
    }.launchIn(viewModelScope)
  }

  fun getTodayAnimeSchedule() {
    val dayInCalendar = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    getAnimeScheduleUseCase(dayInCalendar).onEach {
      _animeScheduleState.value = it
    }.launchIn(viewModelScope)
  }

  fun searchContentByQuery(contentType: ContentType, query: String) {
    if (query.length >= 3) {
      searchContentUseCase(contentType, query, 1).onEach { res ->
        _contentSearchState.value = res
        if (res.error != null) currentPage = 2
      }.launchIn(viewModelScope)
    } else {
      Timber.d("search query must longer")
      currentPage = 1
      _contentSearchState.value =
        ContentSearchState(error = Event("Search query must be at least 3 characters long"))
    }
  }

  fun nextContentPageByQuery(query: String, contentType: ContentType) {
    if (query.length >= 3) {
      searchContentUseCase(contentType, query, currentPage).onEach { res ->

        if (res.isLoading) {
          _contentSearchState.value = _contentSearchState.value.copy(isLoading = true)
        } else if (!res.isLoading) {
          if (res.error != null) {
            currentPage++
            val temp = _contentSearchState.value.data.toMutableList()
            temp.addAll(res.data)
            _contentSearchState.value = ContentSearchState(temp)
          } else {
            _contentSearchState.value = _contentSearchState.value.copy(error = res.error)
          }
        }
      }.launchIn(viewModelScope)
    }
  }
}