package com.machina.jikan_client_compose.domain.use_case.get_content_details

import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.core.exception.MyError
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_details.AnimeDetailsDtoV4
import com.machina.jikan_client_compose.data.remote.dto_v4.anime_details.toContentDetails
import com.machina.jikan_client_compose.data.remote.dto_v4.manga_details.MangaDetailsDtoV4
import com.machina.jikan_client_compose.data.remote.dto_v4.manga_details.toContentDetails
import com.machina.jikan_client_compose.data.repository.AnimeRepository
import com.machina.jikan_client_compose.data.repository.MangaRepository
import com.machina.jikan_client_compose.domain.model.ContentDetails
import com.machina.jikan_client_compose.presentation.content_detail_screen.data.ContentDetailsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class GetContentDetailsUseCase @Inject constructor(
  private val animeRepository: AnimeRepository,
  private val mangaRepository: MangaRepository,
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
        is Resource.Success -> {
          val data = resolveContentType(res.data)
          ContentDetailsState(data)
        }
        is Resource.Error -> ContentDetailsState(error = res.message)
        is Resource.Loading -> ContentDetailsState(isLoading = true)
      }

      emit(state)

    }.flowOn(dispatchers.io)
  }

  private fun resolveContentType(data: Any?): ContentDetails? {
    if (data is AnimeDetailsDtoV4) {
      data.toContentDetails()
    } else if (data is MangaDetailsDtoV4) {
      data.toContentDetails()
    } else {
      null
    }
    val res = when (data) {
      is AnimeDetailsDtoV4 -> data.toContentDetails()
      is MangaDetailsDtoV4 -> data.toContentDetails()
      else -> null
    }


    Timber.d("res resolveContent $res")

    return res
  }
}