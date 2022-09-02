package com.machina.jikan_client_compose.domain.use_case.get_top_anime

import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_top.toAnimeTop
import com.machina.jikan_client_compose.data.repository.AnimeRepository
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalDataModel
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalModel
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeHorizontalListContentState
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeTopState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAnimeTopUseCase @Inject constructor(
  private val repository: AnimeRepository,
  private val dispatchers: DispatchersProvider
) {
  operator fun invoke(page: Int = 1): Flow<AnimeTopState> {
    return flow {
      emit(AnimeTopState.Loading)

      val state = when (val res = repository.getAnimeTopOfAllTime(page)) {
        is Resource.Success -> {
          val data = res.data?.data?.map { it.toAnimeTop() }.orEmpty()
          AnimeTopState(data)
        }
        is Resource.Error -> AnimeTopState(error = Event(res.message))
        is Resource.Loading -> AnimeTopState(isLoading = true)
      }

      emit(state)
    }.flowOn(dispatchers.io)
  }

  fun getAsAnimeHorizontalList(page: Int = 1): Flow<AnimeHorizontalListContentState> {
    return flow {
      emit(AnimeHorizontalListContentState.Loading)

      val state = when (val res = repository.getAnimeTopOfAllTime(page)) {
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
}