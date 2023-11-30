package com.machina.jikan_client_compose.presentation.content_view_all_normal

import android.view.Window
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.machina.jikan_client_compose.OnDestinationChanged
import com.machina.jikan_client_compose.navigation.SerializableNavType
import com.machina.jikan_client_compose.ui.theme.MyColor
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

@Composable
fun ContentViewAllListNav(
	systemUiController: SystemUiController,
	window: Window,
	navController: NavController,
	navArgs: ContentViewAllListNavArgs
) {
	OnDestinationChanged(
		systemUiController = systemUiController,
		color = MyColor.DarkGreyBackground,
		drawOverStatusBar = false,
		window = window
	)

	ContentViewAllListScreen(
		navigator = ContentViewAllScreenNavigator(navController),
		viewModel = hiltViewModel(),
		navArgs = navArgs
	)
}

@Serializable
data class ContentViewAllListNavArgs(
	val title: String,
	val url: String,
	val params: Map<String, String> = mapOf()
) {

	override fun toString(): String {
		return serializeAsValue(this)
	}

	companion object : SerializableNavType<ContentViewAllListNavArgs>(serializer())
}