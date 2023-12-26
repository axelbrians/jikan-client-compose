package com.machina.jikan_client_compose.presentation.home_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.domain.use_case.anime.GetHomeSectionsUseCase
import com.machina.jikan_client_compose.domain.use_case.anime.HomeSection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	private val getHomeSectionsUseCase: GetHomeSectionsUseCase,
	private val dispatcher: DispatchersProvider
) : ViewModel() {

	private val _homeState: MutableStateFlow<HomeState> =
		MutableStateFlow(HomeState.Init)
	val homeState: StateFlow<HomeState>
	    get() = _homeState.asStateFlow()

	fun getHomeSections() {
		viewModelScope.launch(dispatcher.io) {
			val loadingState = _homeState.value.let { state ->
				if (state is HomeState.Success) {
					HomeState.Loading(state.sections)
				} else {
					HomeState.Loading(persistentListOf())
				}
			}
			_homeState.update { loadingState }

			val homeContentList = getHomeSectionsUseCase.invoke()
			_homeState.update { HomeState.Success(homeContentList) }
		}
	}

	override fun onCleared() {
		super.onCleared()
		getHomeSectionsUseCase.onCleared()
	}

	sealed interface HomeState {
		object Init: HomeState

		data class Loading(val sections: ImmutableList<HomeSection>): HomeState

		data class Success(val sections: ImmutableList<HomeSection>): HomeState
	}
}