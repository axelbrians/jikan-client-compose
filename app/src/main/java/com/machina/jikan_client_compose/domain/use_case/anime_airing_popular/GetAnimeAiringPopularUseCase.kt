package com.machina.jikan_client_compose.domain.use_case.anime_airing_popular

import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_airing_popular.toAnimeAiringPopular
import com.machina.jikan_client_compose.data.repository.AnimeRepository
import com.machina.jikan_client_compose.presentation.home_screen.composable.anime_popular_current.state.AnimeAiringPopularState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAnimeAiringPopularUseCase @Inject constructor(
  private val repository: AnimeRepository,
  private val dispatchers: DispatchersProvider
) {

  operator fun invoke(): Flow<AnimeAiringPopularState> {
    return flow {
      emit(AnimeAiringPopularState.Loading)

      val state = when (val res = repository.getAnimeAiringPopular()) {
        is Resource.Success -> {
          val data = res.data?.data?.map {
            it.toAnimeAiringPopular()
          }.orEmpty()

          AnimeAiringPopularState(data)
        }
        is Resource.Error -> AnimeAiringPopularState(error = Event(res.message))
        is Resource.Loading -> AnimeAiringPopularState(isLoading = true)
      }
      emit(state)
    }.flowOn(dispatchers.io)
  }
}