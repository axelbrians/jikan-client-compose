package com.machina.jikan_client_compose.presentation.home_screen.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.jikan_client_compose.domain.model.anime.AnimeAiringPopular
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalDataModel
import com.machina.jikan_client_compose.domain.use_case.anime_airing_popular.GetAnimeAiringPopularUseCase
import com.machina.jikan_client_compose.domain.use_case.anime_schedule.GetAnimeScheduleUseCase
import com.machina.jikan_client_compose.domain.use_case.get_top_anime.GetAnimeTopUseCase
import com.machina.jikan_client_compose.presentation.data.StateListWrapper
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

  private val _animeAiringPopular : MutableState<StateListWrapper<AnimeAiringPopular>> =
    mutableStateOf(StateListWrapper.default())
  val animeAiringPopular : State<StateListWrapper<AnimeAiringPopular>> = _animeAiringPopular

  private val _animeScheduleState : MutableState<StateListWrapper<AnimeVerticalDataModel>> =
    mutableStateOf(StateListWrapper.default())
  val animeScheduleState : State<StateListWrapper<AnimeVerticalDataModel>> = _animeScheduleState

  private val _animeTopState: MutableState<StateListWrapper<AnimeVerticalDataModel>> =
    mutableStateOf(StateListWrapper.default())
  val animeTopState: State<StateListWrapper<AnimeVerticalDataModel>> = _animeTopState

  fun getAnimeAiringPopular() {
    getAnimeAiringPopularUseCase().onEach {
      _animeAiringPopular.value = it
    }.launchIn(viewModelScope)
  }

  fun getTodayAnimeSchedule() {
    getAnimeScheduleUseCase.getAsStateListWrapper().onEach {
      _animeScheduleState.value = it
    }.launchIn(viewModelScope)
  }

  fun getTopAnimeList() {
    getAnimeTopUseCase.getAsStateListWrapper().onEach {
      _animeTopState.value = it
    }.launchIn(viewModelScope)
  }

}