package com.machina.jikan_client_compose.presentation.detail_screen.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.jikan_client_compose.core.DefaultDispatchers
import com.machina.jikan_client_compose.domain.use_case.get_content_details.GetContentDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ContentDetailsViewModel @Inject constructor(
  private val getContentDetailsUseCase: GetContentDetailsUseCase,
  private val dispatchers: DefaultDispatchers
): ViewModel() {

  private val _contentDetailsState : MutableState<ContentDetailsState> = mutableStateOf(ContentDetailsState())
  val contentDetailsState : State<ContentDetailsState> = _contentDetailsState

  fun getContentDetails(contentType: String?, malId: Int?) {
    getContentDetailsUseCase(contentType, malId).onEach { state ->
      _contentDetailsState.value = state
    }.launchIn(viewModelScope)
  }
}