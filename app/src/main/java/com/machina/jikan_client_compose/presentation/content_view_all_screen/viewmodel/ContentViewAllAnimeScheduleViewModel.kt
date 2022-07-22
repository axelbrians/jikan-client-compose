package com.machina.jikan_client_compose.presentation.content_view_all_screen.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.machina.jikan_client_compose.domain.use_case.anime_schedule.GetAnimeScheduleUseCase
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeHorizontalContentState
import com.machina.jikan_client_compose.presentation.home_screen.data.copyKeepData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ContentViewAllAnimeScheduleViewModel @Inject constructor(
  private val getAnimeScheduleUseCase: GetAnimeScheduleUseCase
): ViewModel(), ContentViewAllViewModel {

  private val _contentState: MutableState<AnimeHorizontalContentState> =
    mutableStateOf(AnimeHorizontalContentState())
  override val contentState: State<AnimeHorizontalContentState> = _contentState

  private var currentPage: Int = 1

  override fun getNextContentPart() {
    getAnimeScheduleUseCase().onEach {
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
    }
  }

  private fun appendNextContentPart(
    oldState: AnimeHorizontalContentState,
    newState: AnimeHorizontalContentState
  ) {
    currentPage++
    _contentState.value = AnimeHorizontalContentState(data = oldState.data + newState.data)
  }
}