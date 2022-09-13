package com.machina.jikan_client_compose.domain.use_case.anime_airing_popular

import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.constant.Constant
import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.anime.AnimeService
import com.machina.jikan_client_compose.data.remote.dto.anime_airing_popular.toAnimeAiringPopular
import com.machina.jikan_client_compose.domain.model.anime.AnimeAiringPopular
import com.machina.jikan_client_compose.presentation.data.StateListWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAnimeAiringPopularUseCase @Inject constructor(
  private val repository: AnimeService,
  private val dispatchers: DispatchersProvider
) {

  operator fun invoke(): Flow<StateListWrapper<AnimeAiringPopular>> = flow {
    emit(StateListWrapper.loading())

    val state = when (val res = repository.getAnimeAiringPopular()) {
      is Resource.Success -> {
        val data = res.data?.data?.map {
          it.toAnimeAiringPopular()
        }.orEmpty().take(Constant.HORIZONTAL_CONTENT_LIMIT)

        StateListWrapper(data = data)
      }
      is Resource.Error -> StateListWrapper(error = Event(res.message))
      is Resource.Loading -> StateListWrapper.loading()
    }
    emit(state)
  }.flowOn(dispatchers.io)

}