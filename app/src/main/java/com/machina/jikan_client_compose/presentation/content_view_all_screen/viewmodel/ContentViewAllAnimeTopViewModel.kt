package com.machina.jikan_client_compose.presentation.content_view_all_screen.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.jikan_client_compose.domain.use_case.get_top_anime.GetAnimeTopUseCase
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeHorizontalContentState
import com.machina.jikan_client_compose.presentation.home_screen.data.copyKeepData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ContentViewAllAnimeTopViewModel @Inject constructor(
  private val getAnimeTopUseCase: GetAnimeTopUseCase
): ViewModel(), ContentViewAllViewModel {

  private val _contentState: MutableState<AnimeHorizontalContentState> =
    mutableStateOf(AnimeHorizontalContentState())
  override val contentState: State<AnimeHorizontalContentState> = _contentState

  private var currentPage: Int = 1

  override fun getNextContentPart() {
    getAnimeTopUseCase(currentPage).onEach {
      val animeHorizontalState = AnimeHorizontalContentState.from(it)
      if (it.isLoading) {
        _contentState.value = _contentState.value.copy(isLoading = true)
        return@onEach
      }

      if (!animeHorizontalState.isSuccess()) { // If CURRENT call was failed, only take error and loading
        _contentState.value = _contentState.value.copyKeepData(animeHorizontalState)
        return@onEach
      }

      if ( // If OLD call was failed and OLD data isEmpty
        !_contentState.value.isSuccess() &&
        _contentState.value.data.isEmpty()
      ) {
          currentPage = 2
          _contentState.value = animeHorizontalState
      }

      // Normal case (Success), append new fetched data
      appendNextContentPart(
        _contentState.value,
        animeHorizontalState
      )
    }.launchIn(viewModelScope)
  }

  private fun appendNextContentPart(
    oldState: AnimeHorizontalContentState,
    newState: AnimeHorizontalContentState
  ) {
    currentPage++
    _contentState.value = AnimeHorizontalContentState(data = oldState.data + newState.data)
  }
}