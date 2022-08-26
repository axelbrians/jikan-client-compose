package com.machina.jikan_client_compose.domain.use_case.search_content

import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.repository.AnimeRepository
import com.machina.jikan_client_compose.presentation.content_search_screen.data.FilterSearchState
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchFilterUseCase @Inject constructor(
	private val animeRepository: AnimeRepository,
	private val dispatchers: DispatchersProvider
) {

	operator fun invoke(): Flow<FilterSearchState> {
		return flow {
			emit(FilterSearchState.Loading)

			val hashMapFilter = hashMapOf<String, FilterGroupData>()

			val genreFilter = animeRepository.getAnimeGenresFilter()
			if (genreFilter is Resource.Success) {
				hashMapFilter[genreFilter.data!!.groupKey] = genreFilter.data
			}

			val ratingFilter = FilterGroupData.ContentRatingFilterGroup
			hashMapFilter[ratingFilter.groupKey] = ratingFilter


			// simulate network call
			delay(5_000)
			emit(FilterSearchState(data = hashMapFilter))
		}.flowOn(dispatchers.io)
	}
}