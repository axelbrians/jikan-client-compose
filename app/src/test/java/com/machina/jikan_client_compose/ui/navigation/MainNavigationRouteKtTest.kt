package com.machina.jikan_client_compose.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.machina.jikan_client_compose.core.extensions.defaultUrl
import com.machina.jikan_client_compose.navigation.strongDestination
import com.machina.jikan_client_compose.presentation.content_detail_screen.ContentDetailsArgs
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import org.junit.Test

class MainNavigationRouteKtTest {

	@Test
	fun test() {
		val destination = strongDestination {
			route = "home"
			requiredNav(
				navArgument("navArgs") {
					type = ContentDetailsArgs
				}
			)
			requiredNav(
				navArgument("stringNav") {
					type = NavType.StringType
				}
			)

			optionalNav(
				navArgument("optionalInt") {
					type = NavType.IntType
				}
			)
			optionalNav(
				navArgument("optionalFloat") {
					type = NavType.FloatType
				}
			)
		}

		println(destination.route)
	}

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
