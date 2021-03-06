package com.machina.jikan_client_compose.presentation.content_detail_screen.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.jikan_client_compose.domain.use_case.get_content_details.GetContentDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ContentDetailsViewModel @Inject constructor(
  private val getContentDetailsUseCase: GetContentDetailsUseCase
): ViewModel() {

  private val _contentDetailsState : MutableState<ContentDetailsState> = mutableStateOf(ContentDetailsState())
  val contentDetailsState : State<ContentDetailsState> = _contentDetailsState

  private val _isRefreshing : MutableState<Boolean> = mutableStateOf(false)
  val isRefreshing : State<Boolean> = _isRefreshing


  fun getContentDetails(contentType: String?, malId: Int?, refresh: Boolean = false) {
    getContentDetailsUseCase(contentType, malId).onEach { state ->
      _contentDetailsState.value = state
      if (state.isLoading) {
        _isRefreshing.value = refresh
      } else {
        _isRefreshing.value = false
      }
    }.launchIn(viewModelScope)
  }
}