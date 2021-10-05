package com.machina.jikan_client_compose.data.source

import com.machina.jikan_client_compose.core.Endpoints
import com.machina.jikan_client_compose.data.response.AnimeSearchResponse
import com.machina.jikan_client_compose.data.response.AnimeTopResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeService {

    @GET(Endpoints.TOP_ANIME_URL)
    suspend fun getTopAnimeList(): Response<AnimeTopResponse>

    @GET(Endpoints.SEARCH_URL)
    suspend fun searchAnimeByQuery(
        @Query("q") query: String,
        @Query("page") page: Int
    ): Response<AnimeSearchResponse>
}