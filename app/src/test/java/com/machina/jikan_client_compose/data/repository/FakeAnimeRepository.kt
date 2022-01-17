package com.machina.jikan_client_compose.data.repository

import com.machina.jikan_client_compose.core.exception.Error
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.data.remote.AnimeService
import com.machina.jikan_client_compose.data.remote.dto.AnimeTopDtoKtor
import com.machina.jikan_client_compose.data.remote.dto.ContentDetailsDto
import com.machina.jikan_client_compose.data.remote.dto.ContentSearchDtoKtor
import org.junit.Assert.*

class FakeAnimeRepository: AnimeService {

  private val topAnime = mutableListOf<AnimeTopDtoKtor>()

  var isReturnNetworkError = false


  override suspend fun getTopAnimeList(page: Int): Resource<List<AnimeTopDtoKtor>> {
    val data = AnimeTopDtoKtor(
      1, 1, "title", "url", "imageUrl", "anime", 1, "1", "2", 1, 1.0
    )
    topAnime.add(data)

    return if (isReturnNetworkError) {
      Resource.Error(Error.UNKNOWN_ERROR)
    } else {
      Resource.Success(topAnime.toList())
    }
  }

  override suspend fun searchAnime(query: String, page: Int): Resource<List<ContentSearchDtoKtor>> {
    return Resource.Error(Error.UNKNOWN_ERROR)
  }

  override suspend fun getAnimeDetails(malId: Int): Resource<ContentDetailsDto> {
    return Resource.Error(Error.UNKNOWN_ERROR)
  }
}