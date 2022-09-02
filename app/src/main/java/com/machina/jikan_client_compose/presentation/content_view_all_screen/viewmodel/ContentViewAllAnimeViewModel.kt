package com.machina.jikan_client_compose.presentation.content_view_all_screen.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalModel
import com.machina.jikan_client_compose.domain.use_case.content_view_all.GetContentViewAllUseCase
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeHorizontalListContentState
import com.machina.jikan_client_compose.presentation.home_screen.data.copyKeepData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ContentViewAllAnimeViewModel @Inject constructor(
  private val getContentViewAllUseCase: GetContentViewAllUseCase
): ViewModel() {

  private val _contentState: MutableState<AnimeHorizontalListContentState> =
    mutableStateOf(AnimeHorizontalListContentState())
  val contentState: State<AnimeHorizontalListContentState> = _contentState

  private val jobs: MutableMap<Int, Job?> = mutableMapOf()

  fun getNextContentPart(url: String, params: Map<String, String>) {
    val currentPage = _contentState.value.data.pagination.currentPage + 1

    // Ensure that only one instance of HTTP call is running on specific page
    if (jobs[currentPage] != null) return

    jobs[currentPage] = getContentViewAllUseCase(url, currentPage, params)
      .onEach { newState: AnimeHorizontalListContentState ->
        if (newState.isLoading) {
          _contentState.value = _contentState.value.copy(isLoading = true)
          return@onEach
        }

        // If CURRENT call was failed, only take error and loading
        if (newState.isSuccess().not()) {
          _contentState.value = _contentState.value.copyKeepData(newState)
          return@onEach
        }

        if ( // If OLD call was failed and OLD data isEmpty
          _contentState.value.isSuccess().not() &&
          _contentState.value.data.data.isEmpty()
        ) {
          _contentState.value = newState
          return@onEach
        }

        // Normal case (Success), append new fetched data
        val newData = (_contentState.value.data.data.toSet() + newState.data.data.toSet()).toList()
        _contentState.value = AnimeHorizontalListContentState(
          data = AnimeVerticalModel(
            data = newData,
            pagination = newState.data.pagination
          )
        )
        Timber.d(_contentState.value.data.pagination.toString())
        jobs[currentPage] = null
      }.launchIn(viewModelScope)
  }

  fun hasNextContentPart(): Boolean {
    return _contentState.value.data.pagination.hasNextPage
  }
}