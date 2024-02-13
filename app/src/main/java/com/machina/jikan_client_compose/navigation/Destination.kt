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

	fun getArgKeys(): List<String> {
		return arguments.getKeys()
	}

	fun getRequiredArgKeys(): List<String> {
		return required.getKeys()
	}

	fun getOptionalArgKeys(): List<String> {
		return optional.getKeys()
	}

	// todo: ab
	// To make this support both required and optional, try to make it as DSL again
	fun createDestinationRoute(vararg requiredParams: Pair<String, Any?>): String {
		val builder = StringBuilder(baseRoute)

		requiredParams.forEach {
			it.second?.toString()?.let { arg ->
				builder.append("/$arg")
			}
		}
		return builder.toString()
	}

	private fun List<NamedNavArgument>.getKeys(): List<String> {
		return this.map { it.name }
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

	fun build(): Destination {
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

fun destination(scope: DestinationBuilder.() -> Unit): Destination {
	return DestinationBuilder()
		.apply(scope)
		.build()
}
