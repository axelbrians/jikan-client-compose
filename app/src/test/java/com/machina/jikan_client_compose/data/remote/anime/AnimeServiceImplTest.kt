package com.machina.jikan_client_compose.data.remote.anime

import io.ktor.client.request.HttpRequestBuilder
import org.junit.Test

class AnimeServiceImplTest {

	@Test
	fun `test url http builder`() {
		val builder = HttpRequestBuilder().apply {
			url {
				path("super", "detail")

				path("detail", "home")
			}
		}

		println(builder.build().url)
	}
}