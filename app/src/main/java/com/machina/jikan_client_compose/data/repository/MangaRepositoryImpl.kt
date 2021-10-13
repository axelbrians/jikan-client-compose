package com.machina.jikan_client_compose.data.repository

import com.machina.jikan_client_compose.core.exception.Error
import com.machina.jikan_client_compose.data.network.Resource
import com.machina.jikan_client_compose.data.network.SafeCall
import com.machina.jikan_client_compose.data.remote.MangaService
import com.machina.jikan_client_compose.data.remote.dto.toMangaSearch
import com.machina.jikan_client_compose.data.utils.ErrorConverter
import com.machina.jikan_client_compose.domain.model.ContentSearch
import com.machina.jikan_client_compose.domain.repository.MangaRepository
import java.net.SocketTimeoutException
import javax.inject.Inject

class MangaRepositoryImpl @Inject constructor(
  private val mangaService: MangaService,
  private val errorConverter: ErrorConverter,
  private val safeCall: SafeCall
): MangaRepository {

  override suspend fun searchManga(query: String, page: Int): Resource<List<ContentSearch>> {
    return try {
      val res = mangaService.searchManga(query, page)
      val body = res.body()
      val errorBody = res.errorBody()

      if (res.isSuccessful && body != null) {
        Resource.Success(body.results.map { it.toMangaSearch() })
      } else if (errorBody != null) {
        val error = errorConverter.convertBasicError(errorBody)

        if (error != null) Resource.Error(error.message)
        else Resource.Error(Error.UNKNOWN_ERROR)
      } else {
        Resource.Error(Error.UNKNOWN_ERROR)
      }
    } catch (e: Exception) {
      when(e) {
        is SocketTimeoutException -> Resource.Error(Error.TIMEOUT_ERROR,null)
        else -> Resource.Error(Error.UNKNOWN_ERROR, null)
      }
    }
  }


}