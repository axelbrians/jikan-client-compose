package com.machina.jikan_client_compose.data.remote

import com.machina.jikan_client_compose.core.Endpoints
import com.machina.jikan_client_compose.data.remote.dto.ContentSearchResponse
import com.machina.jikan_client_compose.data.remote.dto.AnimeTopResponse
import com.machina.jikan_client_compose.data.remote.dto.ContentDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeService {

    @GET(Endpoints.TOP_ANIME_URL)
    suspend fun getTopAnimeList(): Response<AnimeTopResponse>

    @GET("${Endpoints.SEARCH_URL}/anime")
    suspend fun searchAnime(
        @Query("q") query: String,
        @Query("page") page: Int
    ): Response<ContentSearchResponse>

    @GET("${Endpoints.SEARCH_URL}/{contentType}")
    suspend fun searchContentByQuery(
        @Path("contentType") contentType: String,
        @Query("q") query: String,
        @Query("page") page: Int
    ): Response<ContentSearchResponse>

    @GET("{contentType}/{malId}")
    suspend fun getContentDetails(
        @Path("contentType") contentType: String,
        @Path("malId") malId: Int,
    ): Response<ContentDetailsResponse>
}