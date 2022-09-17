package com.machina.jikan_client_compose.domain.use_case.content_view_all

import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.anime_search.AnimeSearchService
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalDataModel
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalModel
import com.machina.jikan_client_compose.presentation.data.StateWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetContentViewAllUseCase @Inject constructor(
	private val repository: AnimeSearchService,
	private val dispatchers: DispatchersProvider
) {
	operator fun invoke(
		url: String,
		page: Int = 1,
		params: Map<String, String>
	): Flow<StateWrapper<AnimeVerticalModel>> = flow {
		emit(StateWrapper.loading())

		val state = when (val res = repository.getAnimeViewAll(url, page, params)) {
			is Resource.Success -> {
				StateWrapper(
					data = AnimeVerticalModel(
						data = res.data!!.data.map {
							AnimeVerticalDataModel.from(it)
						},
						pagination = res.data.pagination
					)
				)
			}
			is Resource.Error -> StateWrapper(error = Event(res.message))
			is Resource.Loading -> StateWrapper(isLoading = true)
		}
		emit(state)
	}.flowOn(dispatchers.io)
}