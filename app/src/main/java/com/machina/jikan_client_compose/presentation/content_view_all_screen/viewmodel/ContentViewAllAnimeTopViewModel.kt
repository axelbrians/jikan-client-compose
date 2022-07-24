package com.machina.jikan_client_compose.presentation.content_view_all_screen.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalModel
import com.machina.jikan_client_compose.domain.use_case.get_top_anime.GetAnimeTopUseCase
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeHorizontalListContentState
import com.machina.jikan_client_compose.presentation.home_screen.data.copyKeepData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ContentViewAllAnimeTopViewModel @Inject constructor(
  private val getAnimeTopUseCase: GetAnimeTopUseCase
): ViewModel(), ContentViewAllViewModel {

  private val _contentState: MutableState<AnimeHorizontalListContentState> =
    mutableStateOf(AnimeHorizontalListContentState())
  override val contentState: State<AnimeHorizontalListContentState> = _contentState

  private val jobs: MutableMap<Int, Job?> = mutableMapOf()

  override fun getNextContentPart() {
    val currentPage = _contentState.value.data.pagination.currentPage + 1

    // Ensure that only one instance of HTTP call is running on specific page
    if (jobs[currentPage] != null) return

    jobs[currentPage] = getAnimeTopUseCase.getAsAnimeHorizontalList(currentPage).onEach {
      if (it.isLoading) {
        _contentState.value = _contentState.value.copy(isLoading = true)
        return@onEach
      }

      if (it.isSuccess().not()) { // If CURRENT call was failed, only take error and loading
        _contentState.value = _contentState.value.copyKeepData(it)
        return@onEach
      }

      if ( // If OLD call was failed and OLD data isEmpty
        _contentState.value.isSuccess().not() &&
        _contentState.value.data.data.isEmpty()
      ) {
        _contentState.value = it
        return@onEach
      }

      // Normal case (Success), append new fetched data
      val newData = (_contentState.value.data.data.toSet() + it.data.data.toSet()).toList()
      _contentState.value = AnimeHorizontalListContentState(
        data = AnimeVerticalModel(
          data = newData,
          pagination = it.data.pagination
        )
      )
      Timber.d(_contentState.value.data.pagination.toString())
      jobs[currentPage] = null
    }.launchIn(viewModelScope)
  }

  override fun hasNextContentPart(): Boolean {
    return _contentState.value.data.pagination.hasNextPage
  }
}