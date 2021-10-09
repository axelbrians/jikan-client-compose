package com.machina.jikan_client_compose.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.jikan_client_compose.core.DefaultDispatchers
import com.machina.jikan_client_compose.data.model.AnimeTop
import com.machina.jikan_client_compose.data.network.Status
import com.machina.jikan_client_compose.data.repository.AnimeRepository
import com.machina.jikan_client_compose.data.response.ContentDetailsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentDetailsViewModel @Inject constructor(
  private val animeRepository: AnimeRepository,
  private val dispatchers: DefaultDispatchers
): ViewModel() {

  private var _contentDetails: MutableLiveData<ContentDetailsResponse> = MutableLiveData()
  val contentDetails: LiveData<ContentDetailsResponse> = _contentDetails

  private var _isFetching: MutableLiveData<Boolean> = MutableLiveData(false)
  val isFetching: LiveData<Boolean> = _isFetching

  fun getContentDetails(contentType: String?, malId: Int?) {
    viewModelScope.launch(dispatchers.network) {
      animeRepository.getContentDetails(contentType, malId)
        .onStart { _isFetching.postValue(true) }
        .collect { res ->
          _isFetching.postValue(false)
          if (res.status == Status.SUCCESS) {
            _contentDetails.postValue(res.data)
          }
        }
    }
  }
}