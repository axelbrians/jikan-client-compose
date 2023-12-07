package com.machina.jikan_client_compose.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

open class Destination(
	val destinationParam: DestinationParam
) {

	fun getArgKeys(): List<String> {
		return destinationParam.arguments.getKeys()
	}

	fun getRequiredArgKeys(): List<String> {
		return destinationParam.required.getKeys()
	}

	fun getOptionalArgKeys(): List<String> {
		return destinationParam.optional.getKeys()
	}

	private fun List<NamedNavArgument>.getKeys(): List<String> {
		return this.map { it.name }
	}

	// todo: ab
	// To make this support both required and optional, try to make it as DSL again
	open fun createDestinationRoute(vararg requiredParams: Pair<String, Any?>): String {
		val builder = StringBuilder(destinationParam.route)

		requiredParams.forEach {
			it.second?.toString()?.let { arg ->
				builder.append("/$arg")
			}
		}
		return builder.toString()
	}
}

open class DestinationParam(
	val route: String,
	val required: List<NamedNavArgument> = emptyList(),
	val optional: List<NamedNavArgument> = emptyList(),
	val deepLinks: List<NavDeepLink> = emptyList()
) {
	val graphRoute: String
		get() {
			val stringBuilder = StringBuilder(route)

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

	val arguments: List<NamedNavArgument>
		get() = required + optional
}

class DestinationParamBuilder {

	private val required: MutableList<NamedNavArgument> = mutableListOf()
	private val optional: MutableList<NamedNavArgument> = mutableListOf()
	lateinit var route: String

	fun build(): DestinationParam {
		return DestinationParam(
			route = route,
			required = required,
			optional = optional,
			deepLinks = emptyList()
		)
	}

	fun requiredNav(navArgument:NamedNavArgument) {
		required.add(navArgument)
	}

	fun optionalNav(navArgument: NamedNavArgument) {
		optional.add(navArgument)
	}

}

fun destinationParam(
	initializer: DestinationParamBuilder.() -> Unit
): DestinationParam {
	return DestinationParamBuilder()
		.apply(initializer)
		.build()
}