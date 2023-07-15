package com.machina.jikan_client_compose.domain.use_case.anime_characters

import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.anime_details.AnimeDetailsService
import com.machina.jikan_client_compose.domain.model.anime.AnimePortraitDataModel
import com.machina.jikan_client_compose.presentation.data.StateListWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAnimeCharactersUseCase @Inject constructor(
  private val repository: AnimeDetailsService,
  private val dispatchers: DispatchersProvider
) {

  operator fun invoke(malId: Int): Flow<StateListWrapper<AnimePortraitDataModel>> {
    return flow {
      emit(StateListWrapper.loading())
      val state = when (val res = repository.getAnimeCharacters(malId)) {
        is Resource.Success -> {
          StateListWrapper(res.data?.map {
            AnimePortraitDataModel.from(it)
          }.orEmpty())
        }
        is Resource.Error -> StateListWrapper(error = Event(res.message))
      }

      emit(state)
    }.flowOn(dispatchers.io)
  }
}