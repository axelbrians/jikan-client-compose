package com.machina.jikan_client_compose.navigation

import android.content.res.Configuration
import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer
import kotlin.random.Random

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun StrongArgument() {
	val navController = rememberNavController()
	CompositionLocalProvider(
		LocalTextStyle provides TextStyle(color = Color.White)
	) {
		NavHost(
			navController = navController,
			startDestination = "home",
			modifier = Modifier.fillMaxSize()
		) {
			strongScopedComposable(
				destination = DefaultDestination("home")
			) { _ ->
				Column {
					Text(text = "Hello in TestNav")
					Button(
						onClick = {
							val route = StrongArgumentNavigation
								.constructRoute(Random.nextInt(), "message")
							navController.navigate(route)
						}
					) { Text(text = "Navigate") }
				}
			}

			strongScopedComposable(
				destination = StrongArgumentNavigation
			) { _, wrapper ->
				val parsedContent = wrapper.id
				val referrer = wrapper.message
				Column {
					Text(text = parsedContent.toString())
					Text(text = referrer)
				}
			}
		}
	}
}

class DefaultDestination(
	val route: String
): Navigation {

	override val destination: StrongDestination
		get() = strongDestination {
			this.route = this@DefaultDestination.route
		}
}

interface Navigation {
	val destination: StrongDestination
}

interface NavigationWithArgument<T: Argument>: Navigation {
	val parser: ArgumentParser<T>
}

interface Argument {
	fun serialize(): String
}

object StrongArgumentNavigation: NavigationWithArgument<StrongArgumentNavigation.StrongArgs> {

	const val KEY = "strong_args_key"

	override val destination = strongDestination {
		route = "home/strong"
		requiredNav(
			navArgument(KEY) {
				type = StrongArgs
			}
		)
	}

	override val parser = object : ArgumentParser<StrongArgs> {
		override fun parse(bundle: Bundle?): StrongArgs {
			return StrongArgs.requireGet(bundle, KEY)
		}
	}

	fun constructRoute(id: Int, message: String): String {
		return destination.createDestinationRoute(
			KEY to StrongArgs(id, message)
		)
	}

	@Serializable
	data class StrongArgs(val id: Int, val message: String): Argument {

		override fun serialize(): String {
			return serializeAsValue(this)
		}

		companion object: SerializableNavType<StrongArgs>(serializer())
	}
}
