package com.machina.jikan_client_compose.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.jikan_client_compose.core.DefaultDispatchers
import com.machina.jikan_client_compose.core.enum.ContentType
import com.machina.jikan_client_compose.data.network.Resource
import com.machina.jikan_client_compose.data.repository.AnimeRepositoryImpl
import com.machina.jikan_client_compose.domain.use_case.get_top_anime.GetTopAnimeUseCase
import com.machina.jikan_client_compose.domain.use_case.search_content.SearchContentUseCase
import com.machina.jikan_client_compose.presentation.home_screen.AnimeTopState
import com.machina.jikan_client_compose.presentation.home_screen.ContentSearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val animeRepository: AnimeRepositoryImpl,
  private val getTopAnimeUseCase: GetTopAnimeUseCase,
  private val searchContentUseCase: SearchContentUseCase,
  private val dispatchers: DefaultDispatchers
): ViewModel() {

  private val _animeTopState : MutableState<AnimeTopState> = mutableStateOf(AnimeTopState())
  val animeTopState : State<AnimeTopState> = _animeTopState

  private val _contentSearchState : MutableState<ContentSearchState> = mutableStateOf(ContentSearchState())
  val contentSearchState : State<ContentSearchState> = _contentSearchState

  private var currentPage = 1

  init {
    getTopAnimeList()
  }

  fun getTopAnimeList() {
    getTopAnimeUseCase().onEach {
      _animeTopState.value = it
    }.launchIn(viewModelScope)
  }

  fun searchContentByQuery(contentType: ContentType, query: String) {
    viewModelScope.launch(dispatchers.network) {
      if (query.length >= 3) {
        searchContentUseCase(contentType, query, 1).collect { res ->
          _contentSearchState.value = res
          if (res.error != null) currentPage = 2
        }
      } else {
        _contentSearchState.value = ContentSearchState(error = "Search query must be at least 3 characters long")
      }
    }
  }

  fun nextContentPageByQuery(query: String, contentType: ContentType) {
    viewModelScope.launch(dispatchers.network) {
      if (query.length >= 3) {
        searchContentUseCase(contentType, query, currentPage).collect { res ->

          if (res.isLoading) {
            _contentSearchState.value = _contentSearchState.value.copy(isLoading = true)
          } else if (!res.isLoading) {
            if (res.error != null) {
              currentPage++
              val temp = _contentSearchState.value.data.toMutableList()
              temp.addAll(res.data)
              _contentSearchState.value = ContentSearchState(temp)
            } else {
              _contentSearchState.value = _contentSearchState.value.copy(error = res.error)
            }
          }
        }
      }
    }
  }
}