package com.machina.jikan_client_compose.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDeepLinkDslBuilder
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

open class Destination(
	private val baseRoute: String,
	val arguments: List<NamedNavArgument> = emptyList(),
	val deepLinks: List<NavDeepLink> = emptyList()
) {
	private val requiredArguments = arguments.filter { !it.argument.isNullable }
	private val optionalArguments = arguments.filter { it.argument.isNullable }
	val route: String = constructRoute()

	fun createDestinationRoute(vararg requiredParams: Pair<String, Argument>): String {
		val builder = StringBuilder(baseRoute)

		requiredParams.forEach { (_, value) ->
			builder.append("/${value.serialize()}")
		}
		return builder.toString()
	}

	fun createDestinationRoute(
		required: List<Pair<String, Any?>> = emptyList(),
		optional: List<Pair<String, Any?>> = emptyList()
	): String {
		val builder = StringBuilder(baseRoute)

		required.forEach { (_, value) ->
			builder.append("/")
			builder.append(getArgument(value))
		}

		if (optional.isNotEmpty()) {
			builder.append("?")
			optional.forEachIndexed { index, (key, value) ->
				builder.append("$key=")
				builder.append(getArgument(value))

				if (index < optional.lastIndex) {
					builder.append("&")
				}
			}
		}

		return builder.toString()
	}

	private fun getArgument(value: Any?): String {
		return if (value is Argument?) {
			value?.serialize() ?: "null"
		} else {
			value.toString()
		}
	}

	private fun constructRoute(): String {
		val builder = StringBuilder(baseRoute)

		requiredArguments.forEach { navArgument ->
			builder.append("/{${navArgument.name}}")
		}

		if (optionalArguments.isNotEmpty()) {
			builder.append("?")
			optionalArguments.forEachIndexed { index, navArgument ->
				builder.append("${navArgument.name}={${navArgument.name}}")

				if (index < optionalArguments.lastIndex) {
					builder.append("&")
				}
			}
		}

		return builder.toString()
	}
}

class DestinationBuilder {

	private val required: MutableList<NamedNavArgument> = mutableListOf()
	private val optional: MutableList<NamedNavArgument> = mutableListOf()
	private val arguments: MutableList<NamedNavArgument> = mutableListOf()
	private val deepLinks: MutableList<NavDeepLink> = mutableListOf()
	lateinit var route: String


	fun build(): Destination {
		assert(this::route.isInitialized) { "property 'route' must be set" }

		return Destination(
			baseRoute = route,
			arguments = arguments,
			deepLinks = deepLinks
		)
	}

	fun requiredNav(navArgument: NamedNavArgument) {
		required.add(navArgument)
	}

	fun addNav(key: String, builder: NavArgumentBuilder.() -> Unit) {
		arguments.add(navArgument(key, builder))
	}

	fun optionalNav(navArgument: NamedNavArgument) {
		optional.add(navArgument)
	}

	fun addDeeplink(builder: NavDeepLinkDslBuilder.() -> Unit) {
		deepLinks.add(navDeepLink(builder))
	}
}

inline fun destination(
	scope: DestinationBuilder.() -> Unit
): Destination {
	return DestinationBuilder()
		.apply(scope)
		.build()
}
