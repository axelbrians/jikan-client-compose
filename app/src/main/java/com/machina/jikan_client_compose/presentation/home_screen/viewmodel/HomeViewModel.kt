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
import com.machina.jikan_client_compose.presentation.data.StateListWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.immutableListOf
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	private val getHomeContentUseCase: GetHomeContentUseCase,
	private val dispatcher: DispatchersProvider
) : ViewModel() {

	private val _homeSectionsState: MutableStateFlow<ImmutableList<HomeSection>> =
		MutableStateFlow(persistentListOf())
	val homeSectionsState: StateFlow<ImmutableList<HomeSection>>
	    get() = _homeSectionsState.asStateFlow()

	private val _animeTopState: MutableState<StateListWrapper<AnimePortraitDataModel>> =
		mutableStateOf(StateListWrapper.default())
	val animeTopState: State<StateListWrapper<AnimePortraitDataModel>> = _animeTopState

	fun getHomeContent() {
		viewModelScope.launch(dispatcher.io) {
			val contentList = getHomeContentUseCase.invoke()
			_homeSectionsState.update { contentList }
		}
	}

	override fun onCleared() {
		super.onCleared()
		getHomeContentUseCase.onCleared()
	}
}