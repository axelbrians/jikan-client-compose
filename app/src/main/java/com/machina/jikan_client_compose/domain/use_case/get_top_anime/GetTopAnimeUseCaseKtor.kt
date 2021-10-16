package com.machina.jikan_client_compose.domain.use_case.get_top_anime

import com.machina.jikan_client_compose.core.DefaultDispatchers
import com.machina.jikan_client_compose.core.Resource
import com.machina.jikan_client_compose.data.remote.dto.toAnimeTop
import com.machina.jikan_client_compose.data.repository.AnimeRepositoryImpl
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeTopState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetTopAnimeUseCaseKtor @Inject constructor(
  private val repository: AnimeRepositoryImpl,
  private val dispatchers: DefaultDispatchers
) {
  operator fun invoke(): Flow<AnimeTopState> {
    return flow {
      emit(AnimeTopState(isLoading = true))
      when (val res = repository.getTopAnimeList()) {
        is Resource.Success -> {
          emit(AnimeTopState(res.data?.map { it.toAnimeTop() } ?: emptyList()))
        }
        is Resource.Error -> {
          emit(AnimeTopState(error = res.message))
        }
        is Resource.Loading -> {
          emit(AnimeTopState(isLoading = true))
        }
      }
    }.flowOn(dispatchers.network)
  }
}