package com.machina.jikan_client_compose.navigation

import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NamedNavArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.presentation.content_detail_screen.ContentDetailsArgs
import com.machina.jikan_client_compose.presentation.content_detail_screen.ContentDetailsDestination
import kotlin.random.Random

class ArgumentEntry private constructor(
	private val arguments: Map<String, Any?>
) {

	operator fun <T> get(key: String): T {
		return arguments[key] as T
	}

	val entries = arguments.entries.map { it.key to it.value }

	companion object {
		internal fun create(navArguments: List<NamedNavArgument>, bundle: Bundle?): ArgumentEntry {
			if (bundle == null) return ArgumentEntry(emptyMap())

			val parsedArguments = navArguments.associate { namedNavArgument ->
				val parsed = namedNavArgument.argument.type[bundle, namedNavArgument.name]
				namedNavArgument.name to parsed
			}

			return ArgumentEntry(parsedArguments)
		}
	}
}

@Composable
private fun StrongArgument() {
	val navController = rememberNavController()
	CompositionLocalProvider(
		LocalTextStyle provides TextStyle(
			color = Color.White
		)
	) {
		NavHost(
			navController = navController,
			startDestination = "start",
			modifier = Modifier.fillMaxSize()
		) {
			composable(
				destination = destination {
					route = "start"
				},
				content = { _ ->
					Column {
						Text(text = "Hello in TestNav")
						Button(
							onClick = {
								val route = ContentDetailsDestination.constructRoute(
									ContentDetailsArgs(Random.nextInt(), ContentType.Anime)
								)
								navController.navigate(route)
							}
						) {
							Text(text = "Navigate")
						}
					}
				}
			)

			scopedComposable(
				destination = ContentDetailsDestination.destination
			) { _, argumentEntry ->
				val magicNumber = argumentEntry.get<Int>(ContentDetailsDestination.KEY_MAGIC_NUMBER)
				val contentDetailsArgs = argumentEntry.get<ContentDetailsArgs>(
					ContentDetailsDestination.KEY_CONTENT_DETAIL_ARGS)

				Column {
					Spacer(modifier = Modifier.height(12.dp))
					Text(text = "magicNumber: $magicNumber")
					Spacer(modifier = Modifier.height(12.dp))
					Text(text = "contentDetails: $contentDetailsArgs")
				}
			}
		}
	}
}

@Preview
@Composable
private fun Preview_TestNav() {
	StrongArgument()
}
