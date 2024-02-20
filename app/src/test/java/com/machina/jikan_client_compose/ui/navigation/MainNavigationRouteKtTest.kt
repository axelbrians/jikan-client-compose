package com.machina.jikan_client_compose.ui.navigation

import com.machina.jikan_client_compose.core.extensions.defaultUrl
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import org.junit.Test

class MainNavigationRouteKtTest {

	@Test
	fun foo() {
		val request = HttpRequestBuilder().apply {
			defaultUrl {
				path("page")
				parameter("key", 10)
				path("super", "nice")
			}
		}

		println(request.build().url)
	}
}
