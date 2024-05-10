package com.machina.jikan_client_compose.data.repository

import com.machina.jikan_client_compose.core.SafeCall
import com.machina.jikan_client_compose.core.constant.Endpoints
import com.machina.jikan_client_compose.core.error.GeneralError
import com.machina.jikan_client_compose.core.error.MyError
import com.machina.jikan_client_compose.core.extensions.defaultUrl
import com.machina.jikan_client_compose.core.safeCall
import com.machina.jikan_client_compose.core.wrapper.Resource
import com.machina.jikan_client_compose.core.wrapper.ResponseListWrapper
import com.machina.jikan_client_compose.data.remote.anime.MangaService
import com.machina.jikan_client_compose.data.remote.dto.anime_details.AnimeDetailsDto
import com.machina.jikan_client_compose.data.remote.dto.manga_details.MangaDetailsDtoV4
import com.machina.jikan_client_compose.data.remote.dto.manga_details.MangaDetailsResponseV4
import com.machina.jikan_client_compose.di.AndroidKtorClient
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class MangaRepository(
	@AndroidKtorClient private val client: HttpClient
) : MangaService {

	// TODO: Need regulated class for manga and anime search, currently just focus on anime
	suspend fun searchManga(
		query: String,
		page: Int
	): Resource<ResponseListWrapper<AnimeDetailsDto>> {
		val request = HttpRequestBuilder().apply {
			method = HttpMethod.Get
			defaultUrl { encodedPath = Endpoints.MANGA_SEARCH }
			parameter("q", query)
			parameter("page", page)
		}

		return client.safeCall<ResponseListWrapper<AnimeDetailsDto>, GeneralError>(request)
	}

	override suspend fun getMangaDetails(malId: Int): Resource<MangaDetailsDtoV4> {
		val request = HttpRequestBuilder().apply {
			method = HttpMethod.Get
			defaultUrl { encodedPath = Endpoints.getMangaDetailsEndpoint(malId) }
		}

		val res = client.safeCall<MangaDetailsResponseV4, GeneralError>(request)

		return if (res is Resource.Success && res.data != null) {
			Resource.Success(res.data.data)
		} else {
			Resource.Error(res.message ?: MyError.UNKNOWN_ERROR)
		}
	}
}