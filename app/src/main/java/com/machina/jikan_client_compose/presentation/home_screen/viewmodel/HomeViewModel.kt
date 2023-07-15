package com.machina.jikan_client_compose.presentation.home_screen.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.domain.model.anime.AnimePortraitDataModel
import com.machina.jikan_client_compose.domain.model.anime.AnimeThumbnail
import com.machina.jikan_client_compose.domain.use_case.anime_airing_popular.GetAnimeAiringPopularUseCase
import com.machina.jikan_client_compose.domain.use_case.anime_schedule.GetAnimeScheduleUseCase
import com.machina.jikan_client_compose.domain.use_case.get_top_anime.GetAnimeTopUseCase
import com.machina.jikan_client_compose.presentation.data.StateListWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	private val getAnimeAiringPopularUseCase: GetAnimeAiringPopularUseCase,
	private val getAnimeTopUseCase: GetAnimeTopUseCase,
	private val getAnimeScheduleUseCase: GetAnimeScheduleUseCase,
	private val dispatcher: DispatchersProvider
) : ViewModel() {

	private val _airingPopular: MutableStateFlow<List<AnimeThumbnail>> =
		MutableStateFlow(emptyList())
	val airingPopular = _airingPopular.asStateFlow()

	private val _animeScheduleState : MutableState<StateListWrapper<AnimePortraitDataModel>> =
		mutableStateOf(StateListWrapper.default())
	val animeScheduleState : State<StateListWrapper<AnimePortraitDataModel>> = _animeScheduleState

	private val _animeTopState: MutableState<StateListWrapper<AnimePortraitDataModel>> =
		mutableStateOf(StateListWrapper.default())
	val animeTopState: State<StateListWrapper<AnimePortraitDataModel>> = _animeTopState

	fun sendEvent(event: HomeEvent) {
		when (event) {
			HomeEvent.GetAnimeAiringPopular -> getAnimeAiringPopular()
			HomeEvent.GetAnimeSchedule -> getTodayAnimeSchedule()
			HomeEvent.GetAnimeTop -> getTopAnimeList()
		}
	}

	private fun getAnimeAiringPopular() {
		viewModelScope.launch(dispatcher.io) {
			try {
				val result = getAnimeAiringPopularUseCase.invoke()
				_airingPopular.update { result }
			} catch (exception: Exception) {
				// Handle Error
			}
		}
	}

	private fun getTodayAnimeSchedule() {
		getAnimeScheduleUseCase.getAsStateListWrapper().onEach {
			_animeScheduleState.value = it
		}.launchIn(viewModelScope)
	}

	private fun getTopAnimeList() {
		getAnimeTopUseCase.getAsStateListWrapper().onEach {
			_animeTopState.value = it
		}.launchIn(viewModelScope)
	}

	sealed class HomeEvent {
		object GetAnimeAiringPopular: HomeEvent()
		object GetAnimeSchedule: HomeEvent()
		object GetAnimeTop: HomeEvent()
	}

}