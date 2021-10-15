package com.machina.jikan_client_compose.domain.use_case.search_content

import com.machina.jikan_client_compose.core.DefaultDispatchers
import com.machina.jikan_client_compose.core.enum.ContentType
import com.machina.jikan_client_compose.core.exception.Error.UNKNOWN_ERROR
import com.machina.jikan_client_compose.data.network.Resource
import com.machina.jikan_client_compose.data.remote.dto.ContentSearchDtoKtor
import com.machina.jikan_client_compose.data.remote.dto.toAnimeModel
import com.machina.jikan_client_compose.data.remote.dto.toMangaModel
import com.machina.jikan_client_compose.data.repository.AnimeRepositoryImplKtor
import com.machina.jikan_client_compose.data.repository.MangaRepositoryImplKtor
import com.machina.jikan_client_compose.domain.model.ContentSearch
import com.machina.jikan_client_compose.presentation.home_screen.ContentSearchState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchContentUseCaseKtor @Inject constructor(
  private val animeRepository: AnimeRepositoryImplKtor,
  private val mangaRepository: MangaRepositoryImplKtor,
  private val dispatchers: DefaultDispatchers
) {
  operator fun invoke(contentType: ContentType, query: String, page: Int): Flow<ContentSearchState> {
    return flow {
      emit(ContentSearchState(isLoading = true))

      val res = when(contentType) {
        ContentType.Anime -> animeRepository.searchAnime(query, page)
        ContentType.Manga -> mangaRepository.searchManga(query, page)
        else -> Resource.Error(UNKNOWN_ERROR)
      }

      when (res) {
        is Resource.Success -> {
          val state = ContentSearchState(resolveContentType(contentType, res.data))
          emit(state)
        }
        is Resource.Error -> emit(ContentSearchState(error = res.message))
        is Resource.Loading -> emit(ContentSearchState(isLoading = true))
      }
    }.flowOn(dispatchers.network)
  }

  private fun resolveContentType(contentType: ContentType, contentList: List<ContentSearchDtoKtor>?): List<ContentSearch> {
    val result = when (contentType) {
      ContentType.Anime -> contentList?.map { it.toAnimeModel() }
      ContentType.Manga -> contentList?.map { it.toMangaModel() }
      else -> null
    }

    return result ?: emptyList()
  }
}