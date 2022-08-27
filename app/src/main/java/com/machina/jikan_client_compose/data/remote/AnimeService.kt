package com.machina.jikan_client_compose.data.remote

import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_airing_popular.AnimeAiringPopularResponseV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_schedules.AnimeScheduleResponseV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_top.AnimeTopResponseV4

interface AnimeService {

	suspend fun getAnimeTopOfAllTime(page: Int):
		Resource<AnimeTopResponseV4>

	suspend fun getAnimeAiringPopular():
		Resource<AnimeAiringPopularResponseV4>

	suspend fun getAnimeSchedule(day: Int, page: Int):
		Resource<AnimeScheduleResponseV4>
}