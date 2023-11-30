package com.machina.jikan_client_compose.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.machina.jikan_client_compose.navigation.destinationParam
import com.machina.jikan_client_compose.presentation.content_detail_screen.ContentDetailsArgs
import org.junit.Test

class MainNavigationRouteKtTest {

	@Test
	fun test() {
		val destinationBuilder = destinationParam {
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

		println(destinationBuilder.graphRoute)
	}

	@Test
	fun `given route object with destination should have proper route`() {

	}
}