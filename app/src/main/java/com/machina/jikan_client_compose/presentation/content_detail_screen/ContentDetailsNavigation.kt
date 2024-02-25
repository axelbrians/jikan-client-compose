package com.machina.jikan_client_compose.presentation.content_detail_screen

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.navigation.Argument
import com.machina.jikan_client_compose.navigation.ArgumentParser
import com.machina.jikan_client_compose.navigation.NavigationWithArgument
import com.machina.jikan_client_compose.navigation.Navigator
import com.machina.jikan_client_compose.navigation.NavigatorDelegate
import com.machina.jikan_client_compose.navigation.SerializableNavType
import com.machina.jikan_client_compose.navigation.destination
import com.machina.jikan_client_compose.presentation.content_view_all_normal.ContentViewAllNavigation
import com.machina.jikan_client_compose.presentation.content_view_all_small.SmallContentViewAllNavigation
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

object ContentDetailsNavigation: NavigationWithArgument<ContentDetailsNavigation.ContentDetailsArgs> {
	const val KEY = "contentDetailArgs"

	override val destination = destination {
		// todo: ab
		// DSL karena route wajib ada, harus jadi required
		// Define route nya kalo bisa ngikutin KTOR, pake path based. bisa apped static path atau
		// dynamic (dynamic ini bakal di replace sama argument) static adalah route aslinya.
		// route = "home/content/{}/details"
		route = "home/content/details"
		addArgument(KEY) {
			type = ContentDetailsArgs
		}
	}

	override val parser = object : ArgumentParser<ContentDetailsArgs> {
		override fun parse(bundle: Bundle?): ContentDetailsArgs {
			return ContentDetailsArgs.requireGet(bundle, KEY)
		}
	}

	fun constructRoute(malId: Int, contentType: ContentType): String {
		return destination.createDestinationRoute(
			required = listOf(KEY to ContentDetailsArgs(malId, contentType))
		)
	}

	@Serializable
	data class ContentDetailsArgs(
		val malId: Int,
		val contentType: ContentType
	): Argument {

		override fun serialize(): String {
			return serializeAsValue(this)
		}

		companion object : SerializableNavType<ContentDetailsArgs>(serializer())
	}

	interface Navigator {
		fun navigateToContentDetails(
			malId: Int,
			contentType: ContentType
		)
	}

	class NavigatorDelegate(private val navController: NavController): Navigator {
		override fun navigateToContentDetails(
			malId: Int,
			contentType: ContentType
		) {
			val route = constructRoute(malId, contentType)
			navController.navigate(
				route = route,
				navOptions = navOptions { this.launchSingleTop = true }
			)
		}
	}
}

class ContentDetailsScreenNavigator(navController: NavController) :
	Navigator.WithNavigateUp by NavigatorDelegate(navController),
    ContentDetailsNavigation.Navigator by ContentDetailsNavigation.NavigatorDelegate(navController),
    ContentViewAllNavigation.Navigator by ContentViewAllNavigation.NavigatorDelegate(navController),
	SmallContentViewAllNavigation.Navigator by SmallContentViewAllNavigation.NavigatorDelegate(navController)
