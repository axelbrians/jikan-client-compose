package com.machina.jikan_client_compose.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.jikan_client_compose.core.DefaultDispatchers
import com.machina.jikan_client_compose.core.enum.ContentType
import com.machina.jikan_client_compose.data.model.ContentSearch
import com.machina.jikan_client_compose.data.model.AnimeTop
import com.machina.jikan_client_compose.data.network.Status
import com.machina.jikan_client_compose.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val animeRepository: AnimeRepository,
    private val dispatchers: DefaultDispatchers
): ViewModel() {

    private var _topAnimeList: MutableLiveData<List<AnimeTop>> = MutableLiveData()
    val topAnimeList: LiveData<List<AnimeTop>> = _topAnimeList

    private var _searchList: MutableLiveData<List<ContentSearch>> = MutableLiveData()
    val searchList: LiveData<List<ContentSearch>> = _searchList


    private var _isFetching: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFetching: LiveData<Boolean> = _isFetching

    private var currentPage = 1

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

    fun searchContentByQuery(query: String, contentType: ContentType, page: Int = 1) {
        viewModelScope.launch(dispatchers.network) {
            if (query.length >= 3) {
                animeRepository.searchContentByQuery(contentType.name.lowercase(), query, page)
                    .onStart {
                        if (_searchList.value?.isEmpty() == true) {
                            _isFetching.postValue(true)
                        }
                    }
                    .collect { res ->
                        _isFetching.postValue(false)
                        if (res.status == Status.SUCCESS && res.data != null) {
                            currentPage = 2
                            _searchList.postValue(res.data.results)
                        }
                    }
            } else {
                _searchList.postValue(listOf())
            }
        }
    }

    fun nextContentPageByQuery(query: String, contentType: ContentType) {
        viewModelScope.launch(dispatchers.network) {
            if (query.length >= 3) {
                animeRepository.searchContentByQuery(contentType.name.lowercase(), query, currentPage)
                    .onStart { _isFetching.postValue(true) }
                    .collect { res ->
                        _isFetching.postValue(false)
                        if (res.status == Status.SUCCESS && res.data != null) {
                            currentPage++
                            val temp = _searchList.value?.toMutableList()
                            temp?.addAll(res.data.results)
                            _searchList.postValue(temp)
                        }
                    }
            }
        }
    }

}