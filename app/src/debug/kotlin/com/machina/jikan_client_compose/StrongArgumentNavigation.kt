package com.machina.jikan_client_compose

import android.content.res.Configuration
import android.os.Bundle
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.machina.jikan_client_compose.StrongArgumentNavigation.StrongArgument.*
import com.machina.jikan_client_compose.navigation.ArgumentParser
import com.machina.jikan_client_compose.navigation.Destination
import com.machina.jikan_client_compose.navigation.Navigation
import com.machina.jikan_client_compose.navigation.NavigationWithArgument
import com.machina.jikan_client_compose.navigation.SerializableNavType
import com.machina.jikan_client_compose.navigation.composable
import com.machina.jikan_client_compose.navigation.destination
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.serializer
import timber.log.Timber
import java.sql.Timestamp
import java.util.Date
import kotlin.random.Random

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun StrongArgument() {
	val navController = rememberNavController()
//	val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)

	val viewModel = viewModel<StrongViewModel>()

	CompositionLocalProvider(
		LocalTextStyle provides TextStyle(color = Color.White)
	) {
		NavHost(
			navController = navController,
			startDestination = "home",
			modifier = Modifier.fillMaxSize()
		) {
			composable(route = "home") { _ ->
//				val viewModel = viewModel<StrongViewModel>(viewModelStoreOwner)
				Column {
					Text(text = "Hello in TestNav")
					Button(
						onClick = {
							val argument = StrongArgumentNavigation.StrongArgument.getRandomized()
//							Timber.tag("puyo").d("argument: $argument")

							viewModel.start()
							val route = StrongArgumentNavigation.constructRoute(argument)
							navController.navigate(route)

//							viewModel.argument = argument
//							navController.navigate(StrongNavigation.destination.route)
						}
					) { Text(text = "Navigate") }
				}
			}

			composable(
				navigation = StrongArgumentNavigation
			) { _, argument ->
//				val viewModel = viewModel<StrongViewModel>(viewModelStoreOwner)
//				val argument = remember { viewModel.argument }
//				Text(text = "Some text")
				val dummy = remember {
					viewModel.end()
					Timber.tag("puyo").d("${argument.id} : ${argument.message}")
					1
				}

//				val parsedContent = wrapper.id
//				val referrer = wrapper.message
//				LazyColumn {
//					item {
//						Text(text = parsedContent.toString())
//						Text(text = referrer)
//					}
//					items(wrapper.data) {
//						Text(text = it.toString())
//					}
//					items(wrapper.meta) {
//						Text(text = it.toString())
//					}
//				}
			}
		}
	}
}

object StrongNavigation: Navigation {
	override val destination: Destination
		get() = destination {
			route = "strong"
		}
}

object StrongArgumentNavigation: NavigationWithArgument<StrongArgumentNavigation.StrongArgument> {

	const val KEY = "strong_args_key"

	override val destination = destination {
		route = "home/strong"
		addArgument(KEY) {
			type = StrongNavType
		}
	}

	override val parser = object : ArgumentParser<StrongArgument> {
		override fun parse(bundle: Bundle?): StrongArgument {
			return StrongNavType.requireGet(bundle, KEY)
		}
	}

	fun constructRoute(argument: StrongArgument): String {
		return destination.createDestinationRoute(
			required = listOf(KEY to StrongNavType.serializeAsValue(argument))
		)
	}

	@Serializable
	data class StrongArgument(
		val id: Int,
		val message: String,
		val meta: List<NestedArgument>,
		val data: List<NestedArgument>
	) {
		companion object {
			val Empty = StrongArgument(
				10, "", emptyList(), emptyList()
			)

			val Filled: StrongArgument
				get() {
					val amount = 5
					val moreNestedArgument = List(amount) {
						MoreNestedArgument(Date(), 1_999_777)
					}
					val nested = List(amount) {
						NestedArgument(
							id = amount,
							message = "$amount",
							schedule = moreNestedArgument
						)
					}

					return StrongArgument(amount, amount.toString(), nested, nested)
				}

			fun getRandomized(): StrongArgument {
				val amount = 5
				val moreNestedArgument = List(amount) {
					MoreNestedArgument(Date(Random.nextLong()), Random.nextLong())
				}
				val nested = List(amount) {
					NestedArgument(
						id = Random.nextInt(),
						message = "${Random.nextInt()}",
						schedule = moreNestedArgument
					)
				}

				return StrongArgument(Random.nextInt(), Random.nextInt().toString(), nested.shuffled(), nested.shuffled())
			}
		}
	}

	object StrongNavType: SerializableNavType<StrongArgument>(serializer())
}


@Serializable
data class NestedArgument(
	val id: Int,
	val message: String,
	val schedule: List<MoreNestedArgument>
)

@Serializable
data class MoreNestedArgument(
	@Serializable(with = JavaDateSerializer::class) val date: Date,
	val timestamp: Long
)

object JavaDateSerializer : KSerializer<Date> {
	override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("JavaDate", PrimitiveKind.LONG)

	override fun serialize(encoder: Encoder, value: Date) {
		val timestamp = value.time
		encoder.encodeLong(timestamp)
	}

	override fun deserialize(decoder: Decoder): Date {
		val timestamp = decoder.decodeLong()
		return Date(timestamp)
	}
}
