package com.machina.jikan_client_compose.data.remote.anime_search

import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.core.wrapper.ResponseListWrapper
import com.machina.jikan_client_compose.data.remote.dto.anime_details.AnimeDetailsDto
import com.machina.jikan_client_compose.data.remote.dto.anime_minimal.AnimeMinimalDataResponse
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupData

interface AnimeSearchService {


	suspend fun searchAnime(
		query: String,
		page: Int,
		mapFilter: Map<String, FilterGroupData>
	): Resource<ResponseListWrapper<AnimeDetailsDto>>

	suspend fun getAnimeGenresFilter(): Resource<FilterGroupData>

	suspend fun getAnimeDemographicFilter(): Resource<FilterGroupData>

	suspend fun getAnimeThemesFilter(): Resource<FilterGroupData>

	suspend fun getAnimeExplicitGenresFilter(): Resource<FilterGroupData>


	suspend fun getAnimeViewAll(
		url: String,
		page: Int,
		params: Map<String, String>
	): Resource<ResponseListWrapper<AnimeMinimalDataResponse>>
}