package com.machina.jikan_client_compose.glance

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.LocalGlanceId
import androidx.glance.LocalSize
import androidx.glance.action.Action
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.clickable
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.ImageProvider
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.ContentScale
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontStyle
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextDecoration
import androidx.glance.text.TextStyle
import com.machina.jikan_client_compose.R
import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.domain.model.anime.AnimeThumbnail
import com.machina.jikan_client_compose.domain.use_case.anime_airing_popular.GetAnimeAiringPopularUseCase
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class JikanAppWidgetReceiver: GlanceAppWidgetReceiver() {
	
	@Inject
	lateinit var todayAiringPopularUseCase: GetAnimeAiringPopularUseCase

	override val glanceAppWidget: GlanceAppWidget
		get() = JikanTodayAiringGlanceAppWidget()

	override fun onDeleted(context: Context, appWidgetIds: IntArray) {
		super.onDeleted(context, appWidgetIds)
//		Timber.tag("puyo").d("onDeleted - widgetIds: ${appWidgetIds.toList()}")
	}

	override fun onAppWidgetOptionsChanged(
		context: Context,
		appWidgetManager: AppWidgetManager,
		appWidgetId: Int,
		newOptions: Bundle
	) {
		super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
//		Timber.tag("puyo").d("onAppWidgetOptionChanged - widgetId: $appWidgetId")
	}

	override fun onReceive(context: Context, intent: Intent) {
		super.onReceive(context, intent)
//		Timber.tag("puyo").d("onReceive")
	}

	override fun onUpdate(
		context: Context,
		appWidgetManager: AppWidgetManager,
		appWidgetIds: IntArray
	) {
		super.onUpdate(context, appWidgetManager, appWidgetIds)
//		Timber.tag("puyo").d("onUpdate - widgedIds: ${appWidgetIds.toList()}")
	}
}

class JikanTodayAiringGlanceAppWidget(
//	private val todayAiringPopularUseCase: GetAnimeAiringPopularUseCase
): GlanceAppWidget() {

	companion object {
//		private const val KEY_USE_CASE = "key_use_case"
//		val useCaseActionParameter = ActionParameters
//			.Key<GetAnimeAiringPopularUseCase>(KEY_USE_CASE)

		val sourceKey = stringPreferencesKey("image_source")
		val sourceUrlKey = stringPreferencesKey("image_source_url")

		fun getImageKey(size: DpSize) = getImageKey(size.width.value.toPx, size.height.value.toPx)

		fun getImageKey(width: Float, height: Float) = stringPreferencesKey(
			"uri-$width-$height"
		)
	}

	override suspend fun provideGlance(context: Context, id: GlanceId) {
		provideContent {
			PicsumContent()
//			TodayAiringAnimeListContent()
//			Material3Content()
		}
	}

	/**
	 * Called when the widget instance is deleted. We can then clean up any ongoing task.
	 */
	override suspend fun onDelete(context: Context, glanceId: GlanceId) {
		super.onDelete(context, glanceId)
		JikanGlanceImageWorker.cancel(context, glanceId)
	}

	@Composable
	fun PicsumContent() {
		val context = LocalContext.current
		val size = LocalSize.current
		val imagePath = currentState(getImageKey(size))

//		Timber.tag("puyo").d("width: ${size.width} - height: ${size.height}")
//		Timber.tag("puyo").d("imagePath: $imagePath")
		Box(
			modifier = GlanceModifier
				.appWidgetBackground()
				.background(Color.Black.copy(alpha = 0.8f))
				.fillMaxSize()
				.padding(16.dp),
			contentAlignment = if (imagePath == null) {
				Alignment.Center
			} else {
				Alignment.BottomEnd
			}
		) {
			if (imagePath != null) {
				Image(
					provider = getImageProvider(imagePath),
					contentDescription = "Picsum image",
					contentScale = ContentScale.FillBounds,
					modifier = GlanceModifier
						.fillMaxSize()
						.clickable(
							actionRunCallback<RefreshAction>(
//								actionParametersOf(
//									useCaseActionParameter to todayAiringPopularUseCase
//								)
							)
						)
				)
				Text(
					text = "Source: ${currentState(sourceKey)}",
					style = TextStyle(
						fontSize = 12.sp,
						fontStyle = FontStyle.Italic,
						textAlign = TextAlign.End,
						textDecoration = TextDecoration.Underline
					),
					modifier = GlanceModifier
						.fillMaxWidth()
						.padding(8.dp)
						.background(Color.White.copy(alpha = 0.8f))
						.clickable(
							actionStartActivity(
								Intent(
									Intent.ACTION_VIEW,
									Uri.parse(currentState(sourceUrlKey))
								)
							)
						)
				)
			} else {
				CircularProgressIndicator()

				// Enqueue the worker after the composition is completed using the glanceId as
				// tag so we can cancel all jobs in case the widget instance is deleted
				val glanceId = LocalGlanceId.current
				LaunchedEffect(Unit) {
					JikanGlanceImageWorker.enqueue(context, size, glanceId)
				}
			}
		}
	}

	@Composable
	fun TodayAiringAnimeListContent() {
		val animeList = produceState(initialValue = emptyList()) {
//			val newAnimeList = todayAiringPopularUseCase.invoke()
			val newAnimeList = emptyList<AnimeThumbnail>()
			Timber.tag("puyo").d("fetchedAnime: $newAnimeList")

			kotlinx.coroutines.delay(10.seconds)

			this.value = newAnimeList.take(4)
		}

		Row(
			modifier = GlanceModifier
				.padding(12.dp)
				.appWidgetBackground()
				.background(Color.White.copy(alpha = 0.8f))
				.fillMaxSize(),
			verticalAlignment = Alignment.CenterVertically
		) {
			animeList.value.forEach { anime ->
				Column(
					modifier = GlanceModifier
						.padding(12.dp)
						.background(Color.DarkGray)
						.defaultWeight()
				) {
					Image(
						provider = ImageProvider(resId = R.drawable.ic_filter_solid),
						contentDescription = "Thumbnail",
						contentScale = androidx.glance.layout.ContentScale.Fit,
						modifier = GlanceModifier
							.fillMaxWidth()
					)

					Text(
						text = anime.title,
						style = TextStyle(
							fontSize = 13.sp,
							fontWeight = androidx.glance.text.FontWeight.Medium
						),
					)
				}
			}
		}
	}

	/**
	 * Create an ImageProvider using an URI if it's a "content://" type, otherwise load
	 * the bitmap from the cache file
	 *
	 * Note: When using bitmaps directly your might reach the memory limit for RemoteViews.
	 * If you do reach the memory limit, you'll need to generate a URI granting permissions
	 * to the launcher.
	 *
	 * More info:
	 * https://developer.android.com/training/secure-file-sharing/share-file#GrantPermissions
	 */
	private fun getImageProvider(path: String): ImageProvider {
		if (path.startsWith("content://")) {
			Timber.tag("puyo").d("getting ImageProvider withPath: $path")
			return ImageProvider(path.toUri())
		}


		Timber.tag("puyo").d("getting ImageProvider from bitmap :$path")
		val bitmap = BitmapFactory.decodeFile(path)
		return ImageProvider(bitmap)
	}

	@Composable
	fun GlassOfWaterContent() {
		val counter = remember {
			mutableIntStateOf(0)
		}

		Row(
			modifier = GlanceModifier
				.padding(12.dp)
				.background(Color.White.copy(alpha = 0.8f))
				.fillMaxSize(),
			horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
			verticalAlignment = Alignment.CenterVertically
		) {
			Button(text = "Minus", onClick = { counter.intValue -= 1 })

			Spacer(modifier = GlanceModifier.width(12.dp))

			Column(
				modifier = GlanceModifier.defaultWeight(),
				horizontalAlignment = Alignment.Horizontal.CenterHorizontally
			) {
				Text(
					text = "Today's glass of water",
					style = TextStyle(
						fontSize = 16.sp,
						fontWeight = androidx.glance.text.FontWeight.Medium
					)
				)

				Spacer(modifier = GlanceModifier.height(12.dp))

				Text(
					text = counter.intValue.toString(),
					style = TextStyle(
						fontSize = 20.sp,
						fontWeight = androidx.glance.text.FontWeight.Medium
					)
				)
			}

			Spacer(modifier = GlanceModifier.width(12.dp))

			Button(text = "Plus", onClick = { counter.intValue += 1 })
		}
	}
}

class RefreshAction : ActionCallback {
	override suspend fun onAction(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
		// Clear the state to show loading screen
		updateAppWidgetState(context, glanceId) { prefs ->
			prefs.clear()
		}
		JikanTodayAiringGlanceAppWidget().update(context, glanceId)

		// Enqueue a job for each size the widget can be shown in the current state
		// (i.e landscape/portrait)
		GlanceAppWidgetManager(context).getAppWidgetSizes(glanceId).forEach { size ->
			JikanGlanceImageWorker.enqueue(context, size, glanceId, force = true)
		}
	}
}
