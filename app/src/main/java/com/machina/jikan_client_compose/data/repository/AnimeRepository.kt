package com.machina.jikan_client_compose.data.repository

import com.machina.jikan_client_compose.core.DefaultDispatchers
import com.machina.jikan_client_compose.data.error.GeneralError
import com.machina.jikan_client_compose.data.network.NetworkResponse
import com.machina.jikan_client_compose.data.network.Resource
import com.machina.jikan_client_compose.data.network.SafeCall
import com.machina.jikan_client_compose.data.response.AnimeSearchResponse
import com.machina.jikan_client_compose.data.response.AnimeTopResponse
import com.machina.jikan_client_compose.data.source.AnimeService
import com.machina.jikan_client_compose.data.utils.ErrorConverter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class AnimeRepository @Inject constructor(
    private val animeService: AnimeService,
    private val errorConverter: ErrorConverter,
    private val safeCall: SafeCall
) {


    fun getTopAnimeList(): Flow<Resource<AnimeTopResponse>> {
        return flow {
            val res = safeCall.enqueue(animeService::getTopAnimeList)
            emit(res)
        }
    }

    fun searchAnimeByQuery(query: String, page: Int): Flow<Resource<AnimeSearchResponse>> {
        return flow {
            val res = safeCall.enqueue(query, page, errorConverter::convertBasicError, animeService::searchAnimeByQuery)
            emit(res)
        }
    }

//    fun getTopAnimeList(): Flow<Resource<AnimeTopResponse>> {
//        return flow {
//            val res = animeService.getTopAnimeList()
//            val body = res.body()
//            Timber.d("res $res")
//            if (res.isSuccessful && body != null) {
//                emit(Resource.success(body))
//            }
//            Timber.d("res $res")
//            Timber.d("ah yes man of culture")
//        }
//    }
}