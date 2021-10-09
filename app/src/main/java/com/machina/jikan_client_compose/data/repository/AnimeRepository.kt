package com.machina.jikan_client_compose.data.repository

import com.machina.jikan_client_compose.data.network.Resource
import com.machina.jikan_client_compose.data.network.SafeCall
import com.machina.jikan_client_compose.data.response.ContentSearchResponse
import com.machina.jikan_client_compose.data.response.AnimeTopResponse
import com.machina.jikan_client_compose.data.response.ContentDetailsResponse
import com.machina.jikan_client_compose.data.source.AnimeService
import com.machina.jikan_client_compose.data.utils.ErrorConverter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    fun searchContentByQuery(contentType: String, query: String, page: Int): Flow<Resource<ContentSearchResponse>> {
        return flow {
            val res = safeCall.enqueue(contentType, query, page, errorConverter::convertBasicError, animeService::searchContentByQuery)
            emit(res)
        }
    }

    fun getContentDetails(contentType: String?, malId: Int?): Flow<Resource<ContentDetailsResponse>> {
        return flow {
            val res = safeCall.enqueue(contentType ?: "", malId ?: 0, errorConverter::convertBasicError, animeService::getContentDetails)
            emit(res)
            Timber.d("$res")
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