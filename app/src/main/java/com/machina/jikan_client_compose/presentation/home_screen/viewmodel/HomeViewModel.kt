package com.machina.jikan_client_compose.presentation.home_screen.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.jikan_client_compose.domain.use_case.anime_airing_popular.GetAnimeAiringPopularUseCase
import com.machina.jikan_client_compose.domain.use_case.anime_schedule.GetAnimeScheduleUseCase
import com.machina.jikan_client_compose.domain.use_case.get_top_anime.GetAnimeTopUseCase
import com.machina.jikan_client_compose.presentation.home_screen.composable.anime_popular_current.state.AnimeAiringPopularState
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeHorizontalListContentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val getAnimeAiringPopularUseCase: GetAnimeAiringPopularUseCase,
  private val getAnimeTopUseCase: GetAnimeTopUseCase,
  private val getAnimeScheduleUseCase: GetAnimeScheduleUseCase
) : ViewModel() {

  private val _animeAiringPopular : MutableState<AnimeAiringPopularState> =
    mutableStateOf(AnimeAiringPopularState())
  val animeAiringPopular : State<AnimeAiringPopularState> = _animeAiringPopular

  private val _animeScheduleState : MutableState<AnimeHorizontalListContentState> =
    mutableStateOf(AnimeHorizontalListContentState())
  val animeScheduleState : State<AnimeHorizontalListContentState> = _animeScheduleState

  private val _animeTopState: MutableState<AnimeHorizontalListContentState> =
    mutableStateOf(AnimeHorizontalListContentState())
  val animeTopState: State<AnimeHorizontalListContentState> = _animeTopState

  fun getAnimeAiringPopular() {
    getAnimeAiringPopularUseCase().onEach {
      _animeAiringPopular.value = it
    }.launchIn(viewModelScope)
  }

  fun getTodayAnimeSchedule() {
    getAnimeScheduleUseCase.getAsAnimeHorizontalList().onEach {
      _animeScheduleState.value = it
    }.launchIn(viewModelScope)
  }

  fun getTopAnimeList() {
    getAnimeTopUseCase.getAsAnimeHorizontalList().onEach {
      _animeTopState.value = it
    }.launchIn(viewModelScope)
  }

}