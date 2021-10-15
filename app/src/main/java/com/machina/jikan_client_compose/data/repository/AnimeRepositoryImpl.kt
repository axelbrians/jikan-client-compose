package com.machina.jikan_client_compose.data.repository

import com.machina.jikan_client_compose.core.exception.Error
import com.machina.jikan_client_compose.core.exception.Error.TIMEOUT_ERROR
import com.machina.jikan_client_compose.core.exception.Error.UNKNOWN_ERROR
import com.machina.jikan_client_compose.data.network.Resource
import com.machina.jikan_client_compose.data.network.SafeCall
import com.machina.jikan_client_compose.data.remote.AnimeService
import com.machina.jikan_client_compose.data.remote.dto.*
import com.machina.jikan_client_compose.data.utils.ErrorConverter
import com.machina.jikan_client_compose.domain.model.AnimeSearch
import com.machina.jikan_client_compose.domain.model.AnimeTop
import com.machina.jikan_client_compose.domain.model.ContentSearch
import com.machina.jikan_client_compose.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.net.SocketTimeoutException
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val animeService: AnimeService,
    private val errorConverter: ErrorConverter,
    private val safeCall: SafeCall
): AnimeRepository {

//    fun getTopAnimeList(): Flow<Resource<AnimeTopResponse>> {
//        return flow {
//            emit(Resource.Loading())
//            val res = safeCall.enqueue(animeService::getTopAnimeList)
//            emit(res)
//        }
//    }

    override suspend fun getTopAnimeList(): Resource<List<AnimeTop>> {
        return try {
            val res = animeService.getTopAnimeList()
            val body = res.body()
            val errorBody = res.errorBody()

            if (res.isSuccessful && body != null) {
                Resource.Success(body.top.map { it.toAnimeTop() })
            } else if (errorBody != null) {
                val error = errorConverter.convertBasicError(errorBody)
                if (error != null) Resource.Error(error.message)
                else Resource.Error(UNKNOWN_ERROR)
            } else {
                Resource.Error(UNKNOWN_ERROR)
            }
        } catch (e: Exception) {
            when(e) {
                is SocketTimeoutException -> Resource.Error(TIMEOUT_ERROR,null)
                else -> Resource.Error(UNKNOWN_ERROR, null)
            }
        }
    }

    override suspend fun searchAnime(query: String, page: Int): Resource<List<ContentSearch>> {
        return try {
            val res = animeService.searchAnime(query, page)
            val body = res.body()
            val errorBody = res.errorBody()

            if (res.isSuccessful && body != null) {
                Resource.Success(body.results.map { it.toAnimeSearch() })
            } else if (errorBody != null) {
                val error = errorConverter.convertBasicError(errorBody)
                if (error != null) Resource.Error(error.message)
                else Resource.Error(UNKNOWN_ERROR)
            } else {
                Resource.Error(UNKNOWN_ERROR)
            }
        } catch (e: Exception) {
            when(e) {
                is SocketTimeoutException -> Resource.Error(TIMEOUT_ERROR,null)
                else -> Resource.Error(UNKNOWN_ERROR, null)
            }
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