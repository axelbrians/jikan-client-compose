package com.machina.jikan_client_compose.data.remote

import com.machina.jikan_client_compose.data.network.Resource
import com.machina.jikan_client_compose.data.remote.dto.AnimeTopKtorDto
import com.machina.jikan_client_compose.data.remote.dto.AnimeTopResponse
import io.ktor.client.statement.*
import retrofit2.Response

interface AnimeServiceKtor {

  suspend fun getTopAnimeList(): Resource<List<AnimeTopKtorDto>>
}