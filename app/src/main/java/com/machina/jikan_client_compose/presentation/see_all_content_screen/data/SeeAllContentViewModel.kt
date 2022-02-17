package com.machina.jikan_client_compose.presentation.see_all_content_screen.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.jikan_client_compose.domain.use_case.get_top_anime.GetAnimeTopUseCase
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeTopState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SeeAllContentViewModel @Inject constructor(
  private val getTopAnimeUseCase: GetAnimeTopUseCase
) : ViewModel() {

  private val _animeTopState: MutableState<AnimeTopState> = mutableStateOf(AnimeTopState())
  val animeTopState: State<AnimeTopState> = _animeTopState

  private var currentPage = 1

  fun getContentFirstPage() {
    getTopAnimeUseCase().onEach {
      _animeTopState.value = it
      currentPage++
    }.launchIn(viewModelScope)
  }

  fun getContentNextPage() {
    getTopAnimeUseCase(currentPage).onEach { res ->

      if (res.isLoading) {
        _animeTopState.value = _animeTopState.value.copy(isLoading = true)
      } else if (!res.isLoading) {
        if (res.error.peekContent() != null) {
          currentPage++
          val temp = _animeTopState.value.data.toMutableList()
          temp.addAll(res.data)
          _animeTopState.value = AnimeTopState(temp)
        } else {
          _animeTopState.value = _animeTopState.value.copy(error = res.error)
        }
      }
    }.launchIn(viewModelScope)
  }
}