package com.machina.jikan_client_compose.data.repository

import com.machina.jikan_client_compose.core.SafeCall
import com.machina.jikan_client_compose.core.constant.AnimeConstant
import com.machina.jikan_client_compose.core.constant.AnimeGenres
import com.machina.jikan_client_compose.core.constant.Endpoints
import com.machina.jikan_client_compose.core.error.GeneralError
import com.machina.jikan_client_compose.core.extensions.defaultUrl
import com.machina.jikan_client_compose.core.helper.ParamHelper
import com.machina.jikan_client_compose.core.safeCall
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.core.wrapper.ResponseListWrapper
import com.machina.jikan_client_compose.data.remote.anime_search.AnimeSearchService
import com.machina.jikan_client_compose.data.remote.dto.anime_characters.AnimeCharacterResponse
import com.machina.jikan_client_compose.data.remote.dto.anime_details.AnimeDetailsDto
import com.machina.jikan_client_compose.data.remote.dto.anime_minimal.AnimeMinimalDataResponse
import com.machina.jikan_client_compose.data.remote.dto.anime_recommendations.AnimeRecommendationResponse
import com.machina.jikan_client_compose.data.remote.dto.common.Genre
import com.machina.jikan_client_compose.di.AndroidKtorClient
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupData
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupType
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterItemData
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import timber.log.Timber

class AnimeSearchRepository(
	@AndroidKtorClient private val client: HttpClient
): AnimeSearchService {

	override suspend fun searchAnime(
		query: String,
		page: Int,
		mapFilter: Map<String, FilterGroupData>
	): Resource<ResponseListWrapper<AnimeDetailsDto>> {
		val request = HttpRequestBuilder().apply {
			defaultUrl { encodedPath = Endpoints.ANIME_SEARCH }
			parameter(AnimeConstant.QueryKey, query)
			parameter(AnimeConstant.PageKey, page)
			Timber.d("mapFilter: $mapFilter")
			// Parse genre param since it's on different GroupFilter but passed as one string instance
			// to the server, so we need to combine all of those
			val genreParam = ParamHelper.parseGenreMapFilterToParamString(mapFilter)
			if (genreParam.isNotEmpty()) {
				parameter(AnimeGenres.GenreKey, genreParam)
			}

			mapFilter.forEach { (key: String, data: FilterGroupData) ->
				// Since genre filter is already parsed at @genreParam so we just need to skip
				if (!key.contains(AnimeGenres.GenreKey)) {
					val contentRatingParam = ParamHelper.parseFilterGroupDataToParamString(data)
					if (contentRatingParam.isNotBlank()) {
						parameter(key, contentRatingParam)
					}
				}
			}
		}
		return client.safeCall<ResponseListWrapper<AnimeDetailsDto>, GeneralError>(
			request,
			SafeCall.RetryCount
		)
	}


	override suspend fun getAnimeViewAll(
		url: String,
		page: Int,
		params: Map<String, String>
	): Resource<ResponseListWrapper<AnimeMinimalDataResponse>> {
		val request = HttpRequestBuilder().apply {
			defaultUrl { encodedPath = url }
			parameter(AnimeConstant.PageKey, page)
			params.forEach { (key: String, value: String) ->
				parameter(key, value)
			}
		}

		return when {
			url.contains(Endpoints.ANIME_RECOMMENDATIONS) -> {
				handleAnimeRecommendationCall(request)
			}
			url.contains(Endpoints.ANIME_CHARACTERS) -> {
				handleAnimeCharactersCall(request)
			}
			else -> {
				handleDefaultAnimeViewAllCall(request)
			}
		}
	}

	private suspend fun handleAnimeRecommendationCall(
		request: HttpRequestBuilder
	): Resource<ResponseListWrapper<AnimeMinimalDataResponse>> {
		val res = client.safeCall<ResponseListWrapper<AnimeRecommendationResponse>, GeneralError>(
			request, SafeCall.RetryCount
		)
		return if (res is Resource.Success && res.data != null) {
			Resource.Success(
				ResponseListWrapper(
					res.data.pagination,
					res.data.data.map { AnimeMinimalDataResponse.from(it) }
				)
			)
		} else {
			Resource.Error(res.message)
		}
	}

	private suspend fun handleAnimeCharactersCall(
		request: HttpRequestBuilder
	): Resource<ResponseListWrapper<AnimeMinimalDataResponse>> {
		val res = client.safeCall<ResponseListWrapper<AnimeCharacterResponse>, GeneralError>(
			request, SafeCall.RetryCount
		)
		return if (res is Resource.Success && res.data != null) {
			val orderedCharacters = res.data.data.sortedByDescending { it.favoritesCount }
			Resource.Success(
				ResponseListWrapper(
					res.data.pagination,
					orderedCharacters.map { AnimeMinimalDataResponse.from(it) }
				)
			)
		} else {
			Resource.Error(res.message)
		}
	}

	private suspend fun handleDefaultAnimeViewAllCall(
		request: HttpRequestBuilder
	): Resource<ResponseListWrapper<AnimeMinimalDataResponse>> {
		val res = client.safeCall<ResponseListWrapper<AnimeDetailsDto>, GeneralError>(
			request, SafeCall.RetryCount
		)
		return if (res is Resource.Success && res.data != null) {
			val sortedSchedule = res.data.data.sortedBy { it.rank }.toMutableList()
			val zeroRankCount = sortedSchedule.count { it.rank < 1 }

			for (i in 0 until zeroRankCount) {
				val temp = sortedSchedule.removeFirst()
				sortedSchedule.add(temp)
			}
			Resource.Success(
				ResponseListWrapper(
					res.data.pagination,
					sortedSchedule.map { AnimeMinimalDataResponse.from(it) }
				)
			)
		} else {
			Resource.Error(res.message)
		}
	}

	override suspend fun getAnimeGenresFilter(): Resource<FilterGroupData> {
		val request = HttpRequestBuilder().apply {
			defaultUrl { encodedPath = Endpoints.ANIME_GENRES }
			parameter(AnimeConstant.FilterKey, AnimeGenres.GenreKey)
		}

		val res = client.safeCall<ResponseListWrapper<Genre>, GeneralError>(
			request, SafeCall.RetryCount
		)

		return if (res is Resource.Success) {
			Resource.Success(data = FilterGroupData(
				groupKey = AnimeGenres.GenreKey + "_genres",
				groupName = AnimeConstant.Genres,
				isExpanded = false,
				type = FilterGroupType.Checkable,
				filterData = res.data?.data?.map { FilterItemData.from(it) }.orEmpty()
			))
		} else {
			Resource.Error(res.message)
		}
	}

	override suspend fun getAnimeDemographicFilter(): Resource<FilterGroupData> {
		val request = HttpRequestBuilder().apply {
			defaultUrl { encodedPath = Endpoints.ANIME_GENRES }
			parameter(AnimeConstant.FilterKey, AnimeGenres.DemographicsKey)
		}

		val res = client.safeCall<ResponseListWrapper<Genre>, GeneralError>(
			request, SafeCall.RetryCount
		)

		return if (res is Resource.Success) {
			Resource.Success(data = FilterGroupData(
				groupKey = AnimeGenres.GenreKey + "_demographic",
				groupName = AnimeConstant.Demographic,
				isExpanded = false,
				type = FilterGroupType.Checkable,
				filterData = res.data?.data?.map { FilterItemData.from(it) }.orEmpty()
			))
		} else {
			Resource.Error(res.message)
		}
	}

	override suspend fun getAnimeThemesFilter(): Resource<FilterGroupData> {
		val request = HttpRequestBuilder().apply {
			defaultUrl { encodedPath = Endpoints.ANIME_GENRES }
			parameter(AnimeConstant.FilterKey, AnimeGenres.ThemesKey)
		}

		val res = client.safeCall<ResponseListWrapper<Genre>, GeneralError>(
			request, SafeCall.RetryCount
		)

		return if (res is Resource.Success) {
			Resource.Success(data = FilterGroupData(
				groupKey = AnimeGenres.GenreKey + "_themes",
				groupName = AnimeConstant.Themes,
				isExpanded = false,
				type = FilterGroupType.Checkable,
				filterData = res.data?.data?.map { FilterItemData.from(it) }.orEmpty()
			))
		} else {
			Resource.Error(res.message)
		}
	}

	override suspend fun getAnimeExplicitGenresFilter(): Resource<FilterGroupData> {
		val request = HttpRequestBuilder().apply {
			defaultUrl { encodedPath = Endpoints.ANIME_GENRES }
			parameter(AnimeConstant.FilterKey, AnimeGenres.ExplicitKey)
		}

		val res = client.safeCall<ResponseListWrapper<Genre>, GeneralError>(
			request, SafeCall.RetryCount
		)

		return if (res is Resource.Success) {
			Resource.Success(data = FilterGroupData(
				groupKey = AnimeGenres.GenreKey + "_explicit",
				groupName = AnimeConstant.ExplicitGenre,
				isExpanded = false,
				type = FilterGroupType.Checkable,
				filterData = res.data?.data?.map { FilterItemData.from(it) }.orEmpty()
			))
		} else {
			Resource.Error(res.message)
		}
	}
}