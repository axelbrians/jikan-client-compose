package com.machina.jikan_client_compose.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.jikan_client_compose.core.DefaultDispatchers
import com.machina.jikan_client_compose.data.model.AnimeSearch
import com.machina.jikan_client_compose.data.model.AnimeTop
import com.machina.jikan_client_compose.data.network.Status
import com.machina.jikan_client_compose.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val animeRepository: AnimeRepository,
    private val dispatchers: DefaultDispatchers
): ViewModel() {

    private var _topAnimeList: MutableLiveData<List<AnimeTop>> = MutableLiveData()
    val topAnimeList: LiveData<List<AnimeTop>> = _topAnimeList

    private var _searchAnimeList: MutableLiveData<List<AnimeSearch>> = MutableLiveData()
    val searchAnimeList: LiveData<List<AnimeSearch>> = _searchAnimeList


    private var _isFetching: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFetching: LiveData<Boolean> = _isFetching

    fun getTopAnimeList() {
        viewModelScope.launch(dispatchers.network) {
            animeRepository.getTopAnimeList()
                .onStart { _isFetching.postValue(true) }
                .collect { res ->
                    if (res.status == Status.SUCCESS) {
                        _isFetching.postValue(false)
                        _topAnimeList.postValue(res.data!!.top)
                    }
                }
        }
    }

    fun searchAnimeByQuery(query: String, page: Int = 1) {
        viewModelScope.launch(dispatchers.network) {
            if (query.length >= 3) {
                animeRepository.searchAnimeByQuery(query, page)
                    .onStart {
                        if (_searchAnimeList.value?.isEmpty() == true) {
                            _isFetching.postValue(true)
                        }
                    }
                    .collect { res ->
                        if (res.status == Status.SUCCESS && res.data != null) {
                            if (isFetching.value == true) {
                                _isFetching.postValue(false)
                            }

                            Timber.d("search results size ${res.data.results.size}")
                            _searchAnimeList.postValue(res.data.results)
                        }
                    }
            } else {
                _searchAnimeList.postValue(listOf())
            }
        }
    }
}