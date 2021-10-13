package com.machina.jikan_client_compose.data.remote

import com.machina.jikan_client_compose.core.Endpoints
import com.machina.jikan_client_compose.data.remote.dto.ContentSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MangaService {

  @GET("${Endpoints.SEARCH_URL}/manga")
  suspend fun searchManga(
    @Query("q") query: String,
    @Query("page") page: Int
  ): Response<ContentSearchResponse>
}