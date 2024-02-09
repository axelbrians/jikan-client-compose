package com.machina.jikan_client_compose.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.machina.jikan_client_compose.navigation.TestArgument.Companion.serializer
import com.machina.jikan_client_compose.presentation.content_detail_screen.ContentDetailsArgs
import com.machina.jikan_client_compose.presentation.content_detail_screen.ContentDetailsDestination
import com.machina.jikan_client_compose.presentation.content_view_all_normal.ContentViewAllListDestination
import com.machina.jikan_client_compose.presentation.content_view_all_normal.ContentViewAllListNavArgs
import kotlinx.serialization.Serializable

// todo: ab
//fun <T: Destination> NavGraphBuilder.composable(
//	destination: T,
//	content: @Composable T.(NavBackStackEntry) -> Unit
//) {
//	composable(
//		destinationParam = destination.destinationParam,
//		content = content
//	)
//}

@Serializable
private data class TestArgument(
	val id: Int
) {

	override fun toString(): String {
		return TestArgument.serializeAsValue(this)
	}

	companion object: SerializableNavType<TestArgument>(serializer())
}

@Composable
private fun TestNav() {
	NavHost(
		navController = rememberNavController(),
		startDestination = "start"
	) {
		scopedComposable<TestArgument.Companion>(
			destination = destination {
				route = "start"
				requiredNav(
					navArgument(ContentDetailsDestination.KEY_CONTENT_DETAIL_ARGS) {
						type = ContentDetailsArgs

					}
				)
				requiredNav(
					navArgument(ContentDetailsDestination.KEY_MAGIC_NUMBER) {
						type = NavType.IntType
					}
				)
			},
			content = { navBackStackEntry ->

			}
		)
	}
}

fun <T : SerializableNavType<*>> NavGraphBuilder.scopedComposable(
	destination: Destination,
	enterTransition: EnterTransitionType? = null,
	exitTransition: ExitTransitionType? = null,
	popEnterTransition: EnterTransitionType? = enterTransition,
	popExitTransition: ExitTransitionType? = exitTransition,
	content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
	composable(
		destination = destination,
		enterTransition = enterTransition,
		exitTransition = exitTransition,
		popEnterTransition = popEnterTransition,
		popExitTransition = popExitTransition,
		content = { navBackStackEntry ->
			val kClass = destination.required.first()

			val keys = destination.getRequiredArgKeys()
			val navArgs = ContentViewAllListNavArgs.requireGet(
				bundle = navBackStackEntry.arguments,
				key = ContentViewAllListDestination.KEY_NAV_ARGS
			)

//			content.invoke(this, navBackStackEntry, T)
		}
	)
}

fun NavGraphBuilder.composable(
	destination: Destination,
	enterTransition: EnterTransitionType? = null,
	exitTransition: ExitTransitionType? = null,
	popEnterTransition: EnterTransitionType? = enterTransition,
	popExitTransition: ExitTransitionType? = exitTransition,
	content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
	composable(
		route = destination.route,
		arguments = destination.arguments,
		deepLinks = destination.deepLinks,
		enterTransition = enterTransition,
		exitTransition = exitTransition,
		popEnterTransition = popEnterTransition,
		popExitTransition = popExitTransition,
		content = content
	)
}

//private fun NavGraphBuilder.internalComposable(
//	destinationParam: DestinationParam,
//	enterTransition: EnterTransitionType? = null,
//	exitTransition: ExitTransitionType? = null,
//	popEnterTransition: EnterTransitionType? = enterTransition,
//	popExitTransition: ExitTransitionType? = exitTransition,
//	content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit = { }
//) {
//	composable(
//		route = destinationParam.graphRoute,
//		arguments = destinationParam.arguments,
//		deepLinks = destinationParam.deepLinks,
//		enterTransition = enterTransition,
//		exitTransition = exitTransition,
//		popEnterTransition = popEnterTransition,
//		popExitTransition = popExitTransition,
//		content = content
//	)
//}
