package com.machina.jikan_client_compose.domain.use_case.content_view_all

import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.repository.AnimeSearchRepository
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalDataModel
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalModel
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeHorizontalListContentState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetContentViewAllUseCase @Inject constructor(
	private val repository: AnimeSearchRepository,
	private val dispatchers: DispatchersProvider
) {
	operator fun invoke(
		url: String,
		page: Int = 1,
		params: Map<String, String>
	): Flow<AnimeHorizontalListContentState> = flow {
		emit(AnimeHorizontalListContentState.Loading)

		val state = when (val res = repository.getAnimeViewAll(url, page, params)) {
			is Resource.Success -> {
				AnimeHorizontalListContentState(
					data = AnimeVerticalModel(
						data = res.data!!.data.map {
							AnimeVerticalDataModel.from(it)
						},
						pagination = res.data.pagination
					)
				)
			}
			is Resource.Error -> AnimeHorizontalListContentState(error = Event(res.message))
			is Resource.Loading -> AnimeHorizontalListContentState(isLoading = true)
		}
		emit(state)
	}.flowOn(dispatchers.io)
}