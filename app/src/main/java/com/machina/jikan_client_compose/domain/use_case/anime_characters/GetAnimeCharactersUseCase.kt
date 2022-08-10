package com.machina.jikan_client_compose.domain.use_case.anime_characters

import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.repository.AnimeDetailsRepository
import com.machina.jikan_client_compose.presentation.content_detail_screen.data.AnimeCharacterListState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAnimeCharactersUseCase @Inject constructor(
  private val repository: AnimeDetailsRepository,
  private val dispatchers: DispatchersProvider
) {

  operator fun invoke(malId: Int): Flow<AnimeCharacterListState> {
    return flow {
      emit(AnimeCharacterListState.Loading)
      val res = repository.getAnimeCharacters(malId)
      val state = when (res) {
        is Resource.Success -> {
          AnimeCharacterListState(res.data.orEmpty())
        }
        is Resource.Error -> AnimeCharacterListState(error = Event(res.message))
        is Resource.Loading -> AnimeCharacterListState(isLoading = true)
      }

      emit(state)
    }.flowOn(dispatchers.io)
  }
}