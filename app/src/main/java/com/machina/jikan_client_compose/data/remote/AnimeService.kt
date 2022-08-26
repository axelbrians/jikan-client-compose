package com.machina.jikan_client_compose.data.remote

import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.core.wrapper.ResponseDataListWrapper
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_airing_popular.AnimeAiringPopularResponseV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_details.AnimeDetailsDtoV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_schedules.AnimeScheduleResponseV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_top.AnimeTopResponseV4
import com.machina.jikan_client_compose.data.remote.dto_v4.common.Genre
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupData

interface AnimeService {

	suspend fun getAnimeTopOfAllTime(page: Int):
		Resource<AnimeTopResponseV4>

	suspend fun getAnimeAiringPopular():
		Resource<AnimeAiringPopularResponseV4>

	suspend fun searchAnime(
		query: String,
		page: Int,
		mapFilter: Map<String, FilterGroupData>
	): Resource<ResponseDataListWrapper<AnimeDetailsDtoV4>>

	suspend fun getAnimeGenresFilter(): Resource<FilterGroupData>

	suspend fun getAnimeSchedule(day: Int, page: Int):
		Resource<AnimeScheduleResponseV4>
}