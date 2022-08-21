package com.machina.jikan_client_compose.presentation.content_search_screen.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.domain.model.anime.AnimeHorizontalModel
import com.machina.jikan_client_compose.domain.use_case.search_content.SearchContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
  private val searchContentUseCase: SearchContentUseCase,
): ViewModel() {


  private val _contentSearchState: MutableState<ContentSearchState> =
    mutableStateOf(ContentSearchState.Initial)
  val contentSearchState: State<ContentSearchState> = _contentSearchState


  private var currentPage: Int = 1

  fun searchContentByQuery(contentType: ContentType, query: String) {
    if (query.length >= 3) {
      searchContentUseCase(contentType, query, 1).onEach { res ->
        _contentSearchState.value = res
        if (res.error.peekContent() != null) currentPage = 2
      }.launchIn(viewModelScope)
    } else {
      Timber.d("search query must longer")
      currentPage = 1
      _contentSearchState.value =
        ContentSearchState(error = Event("Search query must be at least 3 characters long"))
    }
  }

  fun nextContentPageByQuery(query: String, contentType: ContentType) {
    if (contentSearchState.value.data.data.isEmpty()) {
      return
    }

    if (query.length >= 3) {
      searchContentUseCase(contentType, query, currentPage).onEach { res ->

        if (res.isLoading) {
          _contentSearchState.value = _contentSearchState.value.copy(isLoading = true)
          return@onEach
        }

        if (res.error.peekContent() != null) {
          currentPage++
          val temp = _contentSearchState.value.data.data.toMutableList()
          temp.addAll(res.data.data)
          _contentSearchState.value = ContentSearchState(
            AnimeHorizontalModel(data = temp, pagination = res.data.pagination)
          )
        } else {
          _contentSearchState.value = _contentSearchState.value.copy(error = res.error)
        }
      }.launchIn(viewModelScope)
    }
  }
}