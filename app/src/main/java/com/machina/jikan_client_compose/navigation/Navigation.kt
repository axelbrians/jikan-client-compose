package com.machina.jikan_client_compose.navigation


interface Navigation {
	val destination: Destination
}

interface NavigationWithArgument<T: Argument>: Navigation {
	val parser: ArgumentParser<T>
}

interface Argument {
	fun serialize(): String
}