package com.machina.jikan_client_compose.navigation

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.presentation.content_detail_screen.ContentDetailArgumentParser
import com.machina.jikan_client_compose.presentation.content_detail_screen.ContentDetailsArgs
import com.machina.jikan_client_compose.presentation.content_detail_screen.ContentDetailsDestination
import kotlin.random.Random

@Preview
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
								val args = ContentDetailsArgs(Random.nextInt(), ContentType.Anime)
								val route = ContentDetailsDestination.constructRoute(
									args = args,
									number = Random.nextInt()
								)
								navController.navigate(route)
							}
						) {
							Text(text = "Navigate")
						}
					}
				}
			)

			strongScopedComposable(
				destination = ContentDetailsDestination.destination,
				argumentParser = ContentDetailArgumentParser(),
			) { _, strongEntry ->


				val magicNumber = strongEntry.malId
				val contentDetailsArgs = strongEntry.contentType

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
