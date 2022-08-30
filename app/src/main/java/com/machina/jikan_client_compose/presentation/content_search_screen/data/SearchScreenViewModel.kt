package com.machina.jikan_client_compose.presentation.content_search_screen.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.jikan_client_compose.core.constant.Constant
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.domain.model.anime.AnimeHorizontalModel
import com.machina.jikan_client_compose.domain.use_case.search_content.SearchContentUseCase
import com.machina.jikan_client_compose.domain.use_case.search_content.SearchFilterUseCase
import com.machina.jikan_client_compose.presentation.content_search_screen.data.event.FilterEvent
import com.machina.jikan_client_compose.presentation.content_search_screen.data.event.SearchEvent
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
  private val searchContentUseCase: SearchContentUseCase,
  private val searchFilterUseCase: SearchFilterUseCase
): ViewModel() {


  private val _contentSearchState: MutableState<ContentSearchState> =
    mutableStateOf(ContentSearchState.Initial)
  val contentSearchState: State<ContentSearchState> = _contentSearchState

  private val _searchFilterState : MutableState<FilterSearchState> =
    mutableStateOf(FilterSearchState.Loading)
  val searchFilterState : State<FilterSearchState> = _searchFilterState

  private var defaultFilterMap: Map<String, FilterGroupData> = mapOf()
  private var currentPage: Int = 1

  private var delayJob: Job = Job()
  private var searchJob: Job = Job()

  // TODO: Improve pagination on SearchContent
  private fun searchContentByQuery(contentType: ContentType, query: String) {
    if (query.length >= 3 || query.isEmpty()) {
      if (searchJob.isActive) {
        searchJob.cancel()
      }
      searchJob = searchContentUseCase(
        contentType = contentType,
        query = query,
        page = 1,
        mapFilter = _searchFilterState.value.data
      ).onEach { res ->
        _contentSearchState.value = res
        if (res.error.peekContent() != null) currentPage = 2
      }.launchIn(viewModelScope)
    } else {
      currentPage = 1
      _contentSearchState.value = ContentSearchState.error(Constant.SEARCH_QUERY_TOO_SHORT)
    }
  }

  private fun nextContentPageByQuery(contentType: ContentType, query: String) {
    if (contentSearchState.value.data.data.isEmpty()) {
      return
    }

    if (query.length >= 3 && !searchJob.isActive) {
      searchContentUseCase(contentType, query, currentPage, _searchFilterState.value.data).onEach { res ->
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

  fun getFilterData() {
    searchFilterUseCase().onEach {
      defaultFilterMap = it.data
      _searchFilterState.value = it
    }.launchIn(viewModelScope)
  }

  fun onSearchEvent(event: SearchEvent) {
    when (event) {
      is SearchEvent.SearchFirstPage -> {
        delayJob.cancel()
        delayJob = viewModelScope.launch {
          delay(1_500)
          searchContentByQuery(event.contentType, event.query)
        }
      }
      is SearchEvent.SearchNextPage -> {
        nextContentPageByQuery(event.contentType, event.query)
      }
    }
  }

  fun onFilterEvent(event: FilterEvent) {
    when (event) {
      is FilterEvent.FilterChanged -> {
        val mapFilter = _searchFilterState.value.data.toMutableMap()
        mapFilter[event.filter.groupKey] = event.filter
        _searchFilterState.value = _searchFilterState.value.copy(data = mapFilter)
      }
      is FilterEvent.FilterApplied -> {
        searchContentByQuery(event.contentType, event.query)
      }
      is FilterEvent.FilterReset -> {
        _searchFilterState.value = _searchFilterState.value.copy(data = defaultFilterMap)
        searchContentByQuery(event.contentType, event.query)
      }
    }
  }
}