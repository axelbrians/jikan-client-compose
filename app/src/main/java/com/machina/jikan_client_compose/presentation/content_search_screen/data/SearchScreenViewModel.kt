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
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

  fun searchContentByQuery(contentType: ContentType, query: String) {
    if (query.length >= 3 || query.isEmpty()) {
      searchContentUseCase(
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

  fun nextContentPageByQuery(query: String, contentType: ContentType) {
    if (contentSearchState.value.data.data.isEmpty()) {
      return
    }

    if (query.length >= 3) {
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

  fun setSearchFilter(filterGroup: FilterGroupData) {
    val mapFilter = _searchFilterState.value.data.toMutableMap()
    mapFilter[filterGroup.groupKey] = filterGroup
    _searchFilterState.value = _searchFilterState.value.copy(data = mapFilter)
  }

  fun resetSearchFilter() {
    _searchFilterState.value = _searchFilterState.value.copy(data = defaultFilterMap)
  }
}