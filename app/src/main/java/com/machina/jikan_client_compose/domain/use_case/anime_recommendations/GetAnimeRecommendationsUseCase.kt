package com.machina.jikan_client_compose.domain.use_case.anime_recommendations

import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.anime_details.AnimeDetailsService
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalDataModel
import com.machina.jikan_client_compose.presentation.content_detail_screen.data.AnimeRecommendationsListState
import com.machina.jikan_client_compose.presentation.data.StateListWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAnimeRecommendationsUseCase @Inject constructor(
    private val repository: AnimeDetailsService,
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

  fun getAsStateListWrapper(
    malId: Int
  ): Flow<StateListWrapper<AnimeVerticalDataModel>> = flow {
    emit(StateListWrapper.loading())
    val state = when (val res = repository.getAnimeRecommendations(malId)) {
      is Resource.Success -> {
        StateListWrapper(res.data.orEmpty())
      }
      is Resource.Error -> StateListWrapper(error = Event(res.message))
      is Resource.Loading -> StateListWrapper(isLoading = true)
    }

    emit(state)
  }.flowOn(dispatchers.io)
}