package com.machina.jikan_client_compose.domain.use_case.get_content_details

import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.core.error.MyError
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.anime.MangaService
import com.machina.jikan_client_compose.data.remote.anime_details.AnimeDetailsService
import com.machina.jikan_client_compose.data.remote.dto.anime_details.AnimeDetailsDto
import com.machina.jikan_client_compose.data.remote.dto.anime_details.toContentDetails
import com.machina.jikan_client_compose.data.remote.dto.manga_details.MangaDetailsDtoV4
import com.machina.jikan_client_compose.data.remote.dto.manga_details.toContentDetails
import com.machina.jikan_client_compose.domain.model.ContentDetails
import com.machina.jikan_client_compose.presentation.content_detail_screen.data.ContentDetailsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetContentDetailsUseCase @Inject constructor(
  private val animeRepository: AnimeDetailsService,
  private val mangaRepository: MangaService,
  private val dispatchers: DispatchersProvider
) {

  operator fun invoke(contentType: String?, malId: Int?): Flow<ContentDetailsState> {
    return flow {
      emit(ContentDetailsState(isLoading = true))
      val type = ContentType.valueOf(contentType ?: "NoValue")
      val res = when(type) {
        ContentType.Anime -> animeRepository.getAnimeDetails(malId ?: 0)
        ContentType.Manga -> mangaRepository.getMangaDetails(malId ?: 0)
        else -> Resource.Error(MyError.UNKNOWN_ERROR)
      }

      val state = when (res) {
        is Resource.Success -> ContentDetailsState(resolveContentType(res.data))
        is Resource.Error -> ContentDetailsState(error = res.message)
        is Resource.Loading -> ContentDetailsState(isLoading = true)
      }

      emit(state)
    }.flowOn(dispatchers.io)
  }

  private fun resolveContentType(data: Any?): ContentDetails? {
    if (data is AnimeDetailsDto) {
      data.toContentDetails()
    } else if (data is MangaDetailsDtoV4) {
      data.toContentDetails()
    } else {
      null
    }
    val res = when (data) {
      is AnimeDetailsDto -> data.toContentDetails()
      is MangaDetailsDtoV4 -> data.toContentDetails()
      else -> null
    }

    return res
  }
}