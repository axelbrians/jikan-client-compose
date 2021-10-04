package com.machina.jikan_client_compose.data.source

import com.machina.jikan_client_compose.data.network.Endpoints
import retrofit2.http.GET

interface AnimeService {

    @GET(Endpoints.TOP_ANIME_URL)
    suspend fun getTopAnimeList()
}