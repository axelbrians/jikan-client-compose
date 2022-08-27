package com.machina.jikan_client_compose.domain.use_case.search_content

import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.repository.AnimeSearchRepository
import com.machina.jikan_client_compose.presentation.content_search_screen.data.FilterSearchState
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchFilterUseCase @Inject constructor(
	private val repository: AnimeSearchRepository,
	private val dispatchers: DispatchersProvider
) {

	operator fun invoke(): Flow<FilterSearchState> {
		return flow {
			emit(FilterSearchState.Loading)

			val hashMapFilter = hashMapOf<String, FilterGroupData>()

			with(repository.getAnimeGenresFilter()) {
				if (this is Resource.Success) {
					hashMapFilter[data!!.groupKey] = data
				}
			}

			with(FilterGroupData.StatusFilterGroup) {
				hashMapFilter[groupKey] = this
			}

			with(repository.getAnimeDemographicFilter()) {
				if (this is Resource.Success) {
					hashMapFilter[data!!.groupKey] = data
				}
			}

			with(FilterGroupData.ContentRatingFilterGroup) {
				hashMapFilter[groupKey] = this
			}

			emit(FilterSearchState(data = hashMapFilter))
		}.flowOn(dispatchers.io)
	}
}