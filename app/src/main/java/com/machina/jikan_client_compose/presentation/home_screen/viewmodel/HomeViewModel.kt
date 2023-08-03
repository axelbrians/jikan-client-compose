package com.machina.jikan_client_compose.presentation.home_screen.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.domain.model.anime.AnimePortraitDataModel
import com.machina.jikan_client_compose.domain.use_case.anime.GetHomeContentUseCase
import com.machina.jikan_client_compose.domain.use_case.anime.HomeSection
import com.machina.jikan_client_compose.domain.use_case.anime_airing_popular.GetAnimeAiringPopularUseCase
import com.machina.jikan_client_compose.domain.use_case.anime_schedule.GetAnimeScheduleUseCase
import com.machina.jikan_client_compose.domain.use_case.get_top_anime.GetAnimeTopUseCase
import com.machina.jikan_client_compose.presentation.data.StateListWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
	private val getHomeContentUseCase: GetHomeContentUseCase,
	private val dispatcher: DispatchersProvider
) : ViewModel() {

	private val _homeSectionsState: MutableStateFlow<List<HomeSection>> =
		MutableStateFlow(emptyList())
	val homeSectionsState: StateFlow<List<HomeSection>>
	    get() = _homeSectionsState.asStateFlow()

	private val _animeTopState: MutableState<StateListWrapper<AnimePortraitDataModel>> =
		mutableStateOf(StateListWrapper.default())
	val animeTopState: State<StateListWrapper<AnimePortraitDataModel>> = _animeTopState

	fun getHomeContent() {
		viewModelScope.launch(dispatcher.io) {
			getHomeContentUseCase.invoke().collect { state ->
				_homeSectionsState.update { state }
			}
		}
	}

	fun sendEvent(event: HomeEvent) {
		when (event) {
			HomeEvent.GetAnimeTop -> getTopAnimeList()
		}
	}

	private fun getTopAnimeList() {
		getAnimeTopUseCase.getAsStateListWrapper().onEach {
			_animeTopState.value = it
		}.launchIn(viewModelScope)
	}

	override fun onCleared() {
		super.onCleared()
		getHomeContentUseCase.onCleared()
	}

	sealed class HomeEvent {
		object GetAnimeTop: HomeEvent()
	}

}