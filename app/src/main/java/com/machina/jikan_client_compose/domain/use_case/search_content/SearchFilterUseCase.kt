package com.machina.jikan_client_compose.domain.use_case.search_content

import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.anime_search.AnimeSearchService
import com.machina.jikan_client_compose.presentation.content_search_screen.data.FilterOptionState
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchFilterUseCase @Inject constructor(
	private val repository: AnimeSearchService,
	private val dispatchers: DispatchersProvider
) {

//  rating: g pg pg13 r17 r rx
//  g all ages
//  pg children
//  pg13 teens older 13
//  r17 violence
//  r mild nudity
//  rx hentai
//
//  Publication demographic
//  shounen, shojo, etc
//
//  status: airing complete upcoming
//
//  order_by: mal_id title type rating start_date end_date episodes score scored_by rank popularity members favorites
//
//  sort: desc asc
//
//  type: tv movie ova special ona music
//
//  sfw: boolean (filter adult entries)
//
//  genres: id of genre with comma as delimitter 1,2,3 etc
//
//  themes:
//
//  genres_exclude: id of genre with comma as delimitter 1,2,3 etc

	operator fun invoke(): Flow<FilterOptionState> {
		return flow {
			emit(FilterOptionState.Loading)

			val mapFilter = mutableMapOf<String, FilterGroupData>()

			// Fetch available ContentRating from local
			with(FilterGroupData.ContentRatingFilterGroup) {
				mapFilter[groupKey] = this
			}

			// Fetch available demographics filter
			with(repository.getAnimeDemographicFilter()) {
				if (this is Resource.Success) {
					mapFilter[data!!.groupKey] = data
				}
			}

			// Fetch available Airing Status from local
			with(FilterGroupData.StatusFilterGroup) {
				mapFilter[groupKey] = this
			}

			// Fetch available genre filter
			with(repository.getAnimeGenresFilter()) {
				if (this is Resource.Success) {
					mapFilter[data!!.groupKey] = data
				}
			}

			// Fetch available explicit_genre filter
			with(repository.getAnimeExplicitGenresFilter()) {
				if (this is Resource.Success) {
					mapFilter[data!!.groupKey] = data
				}
			}

			// Fetch available themes filter
			with(repository.getAnimeThemesFilter()) {
				if (this is Resource.Success) {
					mapFilter[data!!.groupKey] = data
				}
			}

			emit(FilterOptionState(data = mapFilter.toMap()))
		}.flowOn(dispatchers.io)
	}
}