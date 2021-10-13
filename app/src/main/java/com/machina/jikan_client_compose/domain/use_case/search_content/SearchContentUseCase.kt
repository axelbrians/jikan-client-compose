package com.machina.jikan_client_compose.domain.use_case.search_content

import com.machina.jikan_client_compose.core.DefaultDispatchers
import com.machina.jikan_client_compose.core.enum.ContentType
import com.machina.jikan_client_compose.data.network.Resource
import com.machina.jikan_client_compose.domain.repository.AnimeRepository
import com.machina.jikan_client_compose.domain.repository.MangaRepository
import com.machina.jikan_client_compose.presentation.home_screen.ContentSearchState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchContentUseCase @Inject constructor(
  private val animeRepository: AnimeRepository,
  private val mangaRepository: MangaRepository,
  private val dispatchers: DefaultDispatchers
) {
  operator fun invoke(contentType: ContentType, query: String, page: Int): Flow<ContentSearchState> {
    return flow {
      emit(ContentSearchState(isLoading = true))
      val res = if (contentType == ContentType.Anime) {
        animeRepository.searchAnime(query, page)
      } else if (contentType == ContentType.Manga) {
        mangaRepository.searchManga(query, page)
      } else {
        Resource.Error("")
      }
      when (res) {
        is Resource.Success -> {
          emit(ContentSearchState(res.data ?: emptyList()))
        }
        is Resource.Error -> {
          emit(ContentSearchState(error = res.message))
        }
        is Resource.Loading -> {
          emit(ContentSearchState(isLoading = true))
        }
      }
    }.flowOn(dispatchers.network)
  }
}