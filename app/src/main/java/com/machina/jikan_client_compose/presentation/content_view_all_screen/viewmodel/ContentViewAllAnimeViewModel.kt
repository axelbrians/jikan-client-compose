package com.machina.jikan_client_compose.presentation.content_view_all_screen.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.jikan_client_compose.core.error.MyError
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalModel
import com.machina.jikan_client_compose.domain.use_case.content_view_all.GetContentViewAllUseCase
import com.machina.jikan_client_compose.presentation.data.StateWrapper
import com.machina.jikan_client_compose.presentation.data.StateWrapper.Companion.copyKeepData
import com.machina.jikan_client_compose.presentation.data.StateWrapper.Companion.copyNewError
import com.machina.jikan_client_compose.presentation.data.StateWrapper.Companion.isSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ContentViewAllAnimeViewModel @Inject constructor(
  private val getContentViewAllUseCase: GetContentViewAllUseCase
): ViewModel() {

  private val _contentState: MutableState<StateWrapper<AnimeVerticalModel>> =
    mutableStateOf(StateWrapper.loading())
  val contentState: State<StateWrapper<AnimeVerticalModel>> = _contentState

  // Ensure that only one instance of HTTP call is running on specific page
  var isWaiting: Boolean = false
    private set

  fun getNextContentPart(url: String, params: Map<String, String>) {
    val currentPage = nextContentPage()
    isWaiting = true
    getContentViewAllUseCase(url, currentPage, params)
      .onEach { newState: StateWrapper<AnimeVerticalModel> ->
        if (newState.isLoading) {
          _contentState.value = _contentState.value.copy(isLoading = true)
          return@onEach
        } else {
          isWaiting = false
        }

        // If CURRENT call was failed, only take error and loading
        if (newState.isSuccess().not()) {
          _contentState.value = _contentState.value.copyKeepData(newState)
          return@onEach
        }

        if ( // If OLD call was failed and OLD data isEmpty
          _contentState.value.isSuccess().not() &&
          _contentState.value.data?.data?.isEmpty() == true
        ) {
          _contentState.value = newState
          return@onEach
        }

        if (newState.data == null) {
          _contentState.value = _contentState.value.copyNewError(MyError.FETCH_FAILURE)
          return@onEach
        }

        // Normal case (Success), append new fetched data
        val newData = _contentState.value.data?.data.orEmpty() + newState.data.data
        _contentState.value = StateWrapper(
          data = AnimeVerticalModel(
            data = newData,
            pagination = newState.data.pagination
          )
        )
      }.launchIn(viewModelScope)
  }

  fun hasNextContentPart(): Boolean {
    return _contentState.value.data?.pagination?.hasNextPage == true
  }

  private fun nextContentPage(): Int {
    return (_contentState.value.data?.pagination?.currentPage ?: 0) + 1
  }
}