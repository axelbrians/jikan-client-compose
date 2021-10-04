package com.machina.jikan_client_compose.data.repository

import com.machina.jikan_client_compose.data.source.AnimeService
import javax.inject.Inject

class AnimeRepository @Inject constructor(
    private val animeService: AnimeService
) {


}