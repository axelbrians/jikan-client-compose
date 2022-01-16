package com.machina.jikan_client_compose.domain.use_case.get_content_details

import com.machina.jikan_client_compose.core.DefaultDispatchers
import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.enum.ContentType
import com.machina.jikan_client_compose.core.exception.Error
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.dto.ContentDetailsDto
import com.machina.jikan_client_compose.data.remote.dto.toAnimeModel
import com.machina.jikan_client_compose.data.remote.dto.toMangaModel
import com.machina.jikan_client_compose.data.repository.AnimeRepositoryImpl
import com.machina.jikan_client_compose.domain.model.ContentDetails
import com.machina.jikan_client_compose.presentation.detail_screen.data.ContentDetailsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetContentDetailsUseCase @Inject constructor(
  private val animeRepository: AnimeRepositoryImpl,
  private val dispatchers: DispatchersProvider
) {

  operator fun invoke(contentType: String?, malId: Int?): Flow<ContentDetailsState> {
    return flow {
      emit(ContentDetailsState(isLoading = true))
      val type = ContentType.valueOf(contentType ?: "NoValue")
      val res = when(type) {
        ContentType.Anime -> animeRepository.getAnimeDetails(malId ?: 0)
//        ContentType.Manga -> mangaRepository.searchManga(query, page)
        else -> Resource.Error(Error.UNKNOWN_ERROR)
      }

      when (res) {
        is Resource.Success -> {
          val data = resolveContentType(type, res.data)
          val state = ContentDetailsState(data)
          emit(state)
        }
        is Resource.Error -> emit(ContentDetailsState(error = res.message))
        is Resource.Loading -> emit(ContentDetailsState(isLoading = true))
      }
    }.flowOn(dispatchers.io)
  }

  private fun resolveContentType(contentType: ContentType, data: ContentDetailsDto?): ContentDetails? {
    val result = when (contentType) {
      ContentType.Anime -> data?.toAnimeModel()
      ContentType.Manga -> data?.toMangaModel()
      else -> null
    }

    return result
  }
}