package com.machina.jikan_client_compose.domain.use_case.anime_recommendations

import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.repository.AnimeDetailsRepository
import com.machina.jikan_client_compose.presentation.content_detail_screen.data.AnimeRecommendationsListState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAnimeRecommendationsUseCase @Inject constructor(
    private val repository: AnimeDetailsRepository,
    private val dispatchers: DispatchersProvider
) {

  operator fun invoke(malId: Int): Flow<AnimeRecommendationsListState> {
    return flow {
      emit(AnimeRecommendationsListState.Loading)
      val res = repository.getAnimeRecommendations(malId)
      val state = when (res) {
        is Resource.Success -> {
          AnimeRecommendationsListState(res.data.orEmpty())
        }
        is Resource.Error -> AnimeRecommendationsListState(error = Event(res.message))
        is Resource.Loading -> AnimeRecommendationsListState(isLoading = true)
      }

      emit(state)
    }.flowOn(dispatchers.io)
  }
}