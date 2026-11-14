package com.machina.jikan_client_compose.core.extensions

import com.machina.jikan_client_compose.core.constant.Endpoints
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.http.encodedPath
import org.junit.Assert.assertEquals
import org.junit.Test

class KtorExtensionTest {

	@Test
	fun `defaultUrl places endpoint under the v4 api`() {
		val request = HttpRequestBuilder().apply {
			defaultUrl { encodedPath = Endpoints.ANIME_TOP }
			parameter("page", 2)
		}

		assertEquals("https://api.jikan.moe/v4/top/anime?page=2", request.build().url.toString())
	}

	@Test
	fun `defaultUrl handles endpoint without leading slash`() {
		val request = HttpRequestBuilder().apply {
			defaultUrl { encodedPath = "anime/1/characters" }
		}

		assertEquals("https://api.jikan.moe/v4/anime/1/characters", request.build().url.toString())
	}
}
