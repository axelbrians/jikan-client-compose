package com.machina.jikan_client_compose.data.source

import com.machina.jikan_client_compose.core.Endpoints
import com.machina.jikan_client_compose.data.response.ContentSearchResponse
import com.machina.jikan_client_compose.data.response.AnimeTopResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface AnimeService {

    @GET(Endpoints.TOP_ANIME_URL)
    suspend fun getTopAnimeList(): Response<AnimeTopResponse>

    @GET("${Endpoints.SEARCH_URL}/{contentType}")
    suspend fun searchContentByQuery(
        @Path("contentType") contentType: String,
        @Query("q") query: String,
        @Query("page") page: Int
    ): Response<ContentSearchResponse>


}