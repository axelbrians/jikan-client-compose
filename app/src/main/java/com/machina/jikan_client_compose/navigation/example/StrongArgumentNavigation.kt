package com.machina.jikan_client_compose.navigation.example

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
import com.machina.jikan_client_compose.navigation.Argument
import com.machina.jikan_client_compose.navigation.ArgumentParser
import com.machina.jikan_client_compose.navigation.Destination
import com.machina.jikan_client_compose.navigation.Navigation
import com.machina.jikan_client_compose.navigation.NavigationWithArgument
import com.machina.jikan_client_compose.navigation.SerializableNavType
import com.machina.jikan_client_compose.navigation.composable
import com.machina.jikan_client_compose.navigation.destination
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
			composable(
				navigation = DefaultDestination("home")
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

			composable(
				navigation = StrongArgumentNavigation
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

	override val destination: Destination
		get() = destination {
			this.route = this@DefaultDestination.route
		}
}

object StrongArgumentNavigation: NavigationWithArgument<StrongArgumentNavigation.StrongArgs> {

	const val KEY = "strong_args_key"

	override val destination = destination {
		route = "home/strong"
		addArgument(KEY) {
			type = StrongArgs
		}
	}

	override val parser = object : ArgumentParser<StrongArgs> {
		override fun parse(bundle: Bundle?): StrongArgs {
			return StrongArgs.requireGet(bundle, KEY)
		}
	}

	fun constructRoute(id: Int, message: String): String {
		return destination.createDestinationRoute(
			required = listOf(KEY to StrongArgs(id, message))
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
