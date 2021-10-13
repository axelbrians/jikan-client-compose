package com.machina.jikan_client_compose.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.jikan_client_compose.core.DefaultDispatchers
import com.machina.jikan_client_compose.data.network.Resource
import com.machina.jikan_client_compose.data.repository.AnimeRepositoryImpl
import com.machina.jikan_client_compose.data.remote.dto.ContentDetailsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentDetailsViewModel @Inject constructor(
  private val animeRepository: AnimeRepositoryImpl,
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
          if (res is Resource.Success) {
            _contentDetails.postValue(res.data)
          }
        }
    }
  }
}