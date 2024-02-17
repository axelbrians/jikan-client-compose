package com.machina.jikan_client_compose.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

open class Destination(
	private val baseRoute: String,
	val required: List<NamedNavArgument>,
	val optional: List<NamedNavArgument> = emptyList(),
	val deepLinks: List<NavDeepLink> = emptyList()
) {

	val route: String = constructRoute()
	val arguments: List<NamedNavArgument> = required + optional

	fun createDestinationRoute(vararg requiredParams: Pair<String, Argument?>): String {
		val builder = StringBuilder(baseRoute)

		requiredParams.forEach { (_, value) ->
			value?.let {
				builder.append("/${it.serialize()}")
			}
		}
		return builder.toString()
	}

	private fun constructRoute(): String {
		val stringBuilder = StringBuilder(baseRoute)

		required.forEach { navArgument ->
			stringBuilder.append("/{${navArgument.name}}")
		}

		if (optional.isNotEmpty()) {
			stringBuilder.append("?")
		}

		optional.forEachIndexed { index, navArgument ->
			stringBuilder.append("${navArgument.name}={${navArgument.name}}")

			if (index < optional.lastIndex) {
				stringBuilder.append("&")
			}
		}

		return stringBuilder.toString()
	}
}

class DestinationBuilder {

	private val required: MutableList<NamedNavArgument> = mutableListOf()
	private val optional: MutableList<NamedNavArgument> = mutableListOf()
	lateinit var route: String

	// Bikin ini biar bisa terima path/argument di tengah
	fun build(): Destination {
		assert(this::route.isInitialized) { "property 'route' must be set" }

		return Destination(
			baseRoute = route,
			required = required,
			optional = optional,
			deepLinks = emptyList()
		)
	}

	fun requiredNav(navArgument: NamedNavArgument) {
		required.add(navArgument)
	}

	fun optionalNav(navArgument: NamedNavArgument) {
		optional.add(navArgument)
	}
}

inline fun destination(
	scope: DestinationBuilder.() -> Unit
): Destination {
	return DestinationBuilder()
		.apply(scope)
		.build()
}
