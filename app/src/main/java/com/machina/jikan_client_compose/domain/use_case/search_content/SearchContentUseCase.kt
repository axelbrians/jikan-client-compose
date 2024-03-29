package com.machina.jikan_client_compose.domain.use_case.search_content

import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.core.error.MyError.UNKNOWN_ERROR
import com.machina.jikan_client_compose.core.wrapper.Event
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.anime.MangaService
import com.machina.jikan_client_compose.data.remote.anime_search.AnimeSearchService
import com.machina.jikan_client_compose.data.remote.dto.anime_details.AnimeDetailsDto
import com.machina.jikan_client_compose.domain.model.anime.AnimeHorizontalDataModel
import com.machina.jikan_client_compose.domain.model.anime.AnimeHorizontalModel
import com.machina.jikan_client_compose.presentation.content_search_screen.data.ContentSearchState
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.FilterGroupData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchContentUseCase @Inject constructor(
  private val repository: AnimeSearchService,
  private val mangaRepository: MangaService,
  private val dispatchers: DispatchersProvider
) {

  // TODO: Handle different type of content in the future
  // TODO: Handle applied filter
  operator fun invoke(
    contentType: ContentType,
    query: String,
    page: Int = 1,
    mapFilter: Map<String, FilterGroupData>
  ): Flow<ContentSearchState> {
    return flow {
      emit(ContentSearchState.Loading)

      val res = when(contentType) {
        ContentType.Anime -> repository.searchAnime(query, page, mapFilter)
//        ContentType.Manga -> mangaRepository.searchManga(query, page)
        ContentType.Manga -> Resource.Error(UNKNOWN_ERROR)
        else -> Resource.Error(UNKNOWN_ERROR)
      }

      val state = when (res) {
        is Resource.Success -> {
          val dataSet = resolveContentType(contentType, res.data!!.data)
          ContentSearchState(
            AnimeHorizontalModel(data = dataSet, pagination = res.data.pagination)
          )
        }
        is Resource.Error -> ContentSearchState(error = Event(res.message))
      }

      emit(state)
    }
  }

  private fun resolveContentType(
    contentType: ContentType,
    contentList: List<AnimeDetailsDto>?
  ): List<AnimeHorizontalDataModel> {
    val result = when (contentType) {
      ContentType.Anime -> contentList?.map { AnimeHorizontalDataModel.from(it) }
//      ContentType.Manga -> contentList?.map { it.toMangaModel() }
      else -> null
    }.orEmpty()

    return result
  }
}