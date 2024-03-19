package com.machina.jikan_client_compose.navigation

import androidx.navigation.NavType
import com.machina.jikan_client_compose.StrongArgumentNavigation
import junit.framework.Assert.assertEquals
import org.junit.Test

class DestinationArgumentBuilderTest {

	@Test
	fun `test iterator`() {
		val sequence = sequenceOf(1, 2 ,3 ,4)

		val iterator = sequence.iterator()

		println(iterator.next())
		println(iterator.next())
		println(iterator.next())
		println(iterator.next())
	}

	@Test
	fun `test nullable default on namedNavArgument`() {
		val destination = destination {
			route = "root"
			addArgument("user-id") { }
			addArgument("strong-args") {
				type = StrongArgumentNavigation.StrongNavType
				nullable = true
			}
			addArgument("second-id") {
				type = NavType.IntType
				nullable = false
				defaultValue = null
			}
		}
	}

	@Test(expected = IllegalArgumentException::class)
	fun `given destination nav type is not allowed as nullable, if set as nullable at destination, then should throw exception`() {
		destination {
			route = "root"
			addArgument("strong-args") {
				type = StrongArgumentNavigation.StrongNavType
				nullable = true
			}
		}
	}

	@Test
	fun `given build destination route, when required and optional are empty, SHOULD create with base route`() {
		val baseRoute = "root"
		val destination = destination {
			route = baseRoute
		}

		assertEquals(baseRoute, destination.createDestinationRoute(emptyList()))
	}

	@Test
	fun `given destination with required argument, should construct route with slash separated`() {
		val baseRoute = "root"
		val destination = destination {
			route = baseRoute
			addArgument("argument") {
				type = NavType.IntType
			}
		}
		val expected = "root/null"

		val actual = destination.createDestinationRoute(required = listOf("argument" to (null as Int?)))
		assertEquals(expected, actual)
	}

	@Test
	fun `given destination with optional argument, should construct route with ampercent separated`() {
		val baseRoute = "root"
		val destination = destination {
			route = baseRoute
		}
		val expected = "root?argument=null"

		val actual = destination.createDestinationRoute(optional = listOf("argument" to (null as Int?)))
		assertEquals(expected, actual)
	}
}