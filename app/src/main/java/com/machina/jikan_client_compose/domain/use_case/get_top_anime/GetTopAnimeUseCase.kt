package com.machina.jikan_client_compose.domain.use_case.get_top_anime

import com.machina.jikan_client_compose.core.DefaultDispatchers
import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.dto.toAnimeTop
import com.machina.jikan_client_compose.data.repository.AnimeRepositoryImpl
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeTopState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetTopAnimeUseCase @Inject constructor(
  private val repository: AnimeRepositoryImpl,
  private val dispatchers: DispatchersProvider
) {
  operator fun invoke(page: Int = 1): Flow<AnimeTopState> {
    return flow {
      emit(AnimeTopState(isLoading = true))

      when (val res = repository.getTopAnimeList(page)) {
        is Resource.Success -> {
          emit(AnimeTopState(res.data?.map { it.toAnimeTop() } ?: emptyList()))
        }
        is Resource.Error -> {
          emit(AnimeTopState(error = Event(res.message)))
        }
        is Resource.Loading -> {
          emit(AnimeTopState(isLoading = true))
        }
      }
    }.flowOn(dispatchers.io)
  }
}