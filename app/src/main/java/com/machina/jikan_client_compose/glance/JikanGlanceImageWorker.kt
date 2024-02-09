package com.machina.jikan_client_compose.glance

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.ui.unit.DpSize
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.FileProvider.getUriForFile
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import coil.annotation.ExperimentalCoilApi
import coil.imageLoader
import coil.memory.MemoryCache
import coil.request.ErrorResult
import coil.request.ImageRequest
import com.machina.jikan_client_compose.R
import timber.log.Timber
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt


class JikanGlanceImageWorker(
	private val context: Context,
	workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {

	companion object {
		private val channelId = "image_worker_channel_id"
		private val notificationId = 1000
		private val uniqueWorkName = JikanGlanceImageWorker::class.java.simpleName

		fun enqueue(context: Context, size: DpSize, glanceId: GlanceId, force: Boolean = false) {
			Timber.tag("puyo").d("enque work - glanceId: $glanceId | size: $size")
			val manager = WorkManager.getInstance(context)
			val requestBuilder = OneTimeWorkRequestBuilder<JikanGlanceImageWorker>().apply {
				addTag(glanceId.toString())
				setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
				setInputData(
					Data.Builder()
						.putFloat("width", size.width.value.toPx)
						.putFloat("height", size.height.value.toPx)
						.putBoolean("force", force)
						.build()
				)
			}
			val workPolicy = if (force) {
				ExistingWorkPolicy.REPLACE
			} else {
				ExistingWorkPolicy.KEEP
			}

			manager.enqueueUniqueWork(
				uniqueWorkName + size.width.value.toPx + size.height.value.toPx,
				workPolicy,
				requestBuilder.build()
			)

			// Temporary workaround to avoid WM provider to disable itself and trigger an
			// app widget update
			manager.enqueueUniqueWork(
				"$uniqueWorkName-workaround",
				ExistingWorkPolicy.KEEP,
				OneTimeWorkRequestBuilder<JikanGlanceImageWorker>().apply {
					this.setInitialDelay(365, TimeUnit.DAYS)
				}.build()
			)
		}

		/**
		 * Cancel any ongoing worker
		 */
		fun cancel(context: Context, glanceId: GlanceId) {
			Timber.tag("puyo").d("cancel work - glanceId: $glanceId")
			WorkManager.getInstance(context).cancelAllWorkByTag(glanceId.toString())
		}
	}

	override suspend fun doWork(): Result {
		return try {
			Timber.tag("puyo").d("doing work")
			val width = inputData.getFloat("width", 0f)
			val height = inputData.getFloat("height", 0f)
			val force = inputData.getBoolean("force", false)
			val uri = getRandomImage(width, height, force)

			updateImageWidget(width, height, uri)
			Result.success()
		} catch (e: Exception) {
			Timber.tag("puyo").e("Error while loading image: $e")
			if (runAttemptCount < 10) {
				// Exponential backoff strategy will avoid the request to repeat
				// too fast in case of failures.
				Result.retry()
			} else {
				Result.failure()
			}
		}
	}

	override suspend fun getForegroundInfo(): ForegroundInfo {
		createNotificationChannelIfNotExist()

		val notificationBuilder = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
			Notification.Builder(context, channelId)
		} else {
			Notification.Builder(context)
		}

		val notification = notificationBuilder.apply {
			setContentTitle("Loading new image")
			setSmallIcon(R.drawable.ic_clock_outlined)
		}.build()

		return ForegroundInfo(
			/* notificationId = */ notificationId,
			/* notification = */ notification
		)
	}

	private fun createNotificationChannelIfNotExist() {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
			val notificationManager =
				getSystemService(context, NotificationManager::class.java) ?: return

			val isChannelAlreadyExist = notificationManager.getNotificationChannel(channelId) != null

			if (isChannelAlreadyExist) return

			val channel = NotificationChannel(
				channelId,
				"Jikan Image Viewer",
				NotificationManager.IMPORTANCE_LOW
			).apply {
				description = "Jikan channel for foreground service notification"
			}

			notificationManager.createNotificationChannel(channel)
		}
	}

	private suspend fun updateImageWidget(width: Float, height: Float, uri: String) {
		Timber.tag("puyo").d("updating glance with uri: $uri")
		val manager = GlanceAppWidgetManager(context)
		val glanceIds = manager.getGlanceIds(JikanTodayAiringGlanceAppWidget::class.java)
		glanceIds.forEach { glanceId ->
			Timber.tag("puyo").d("updating glance state for: $glanceId")
			updateAppWidgetState(context, glanceId) { prefs ->
				prefs[JikanTodayAiringGlanceAppWidget.getImageKey(width, height)] = uri
				prefs[JikanTodayAiringGlanceAppWidget.sourceKey] = "Picsum Photos"
				prefs[JikanTodayAiringGlanceAppWidget.sourceUrlKey] = "https://picsum.photos/"
			}
		}
		JikanTodayAiringGlanceAppWidget().updateAll(context)
	}

	/**
	 * Use Coil and Picsum Photos to randomly load images into the cache based on the provided
	 * size. This method returns the path of the cached image, which you can send to the widget.
	 */
	@OptIn(ExperimentalCoilApi::class)
	private suspend fun getRandomImage(width: Float, height: Float, force: Boolean): String {
		val url = "https://picsum.photos/${width.roundToInt()}/${height.roundToInt()}"
		val request = ImageRequest.Builder(context)
			.data(url)
			.build()

		val imageLoader = context.imageLoader

		// Request the image to be loaded and throw error if it failed
		if (true) {
			Timber.tag("puyo").d("clearing image cache")
			imageLoader.diskCache?.remove(url)
			imageLoader.memoryCache?.remove(MemoryCache.Key(url))
		}
		val result = imageLoader.execute(request)
		if (result is ErrorResult) {
			throw result.throwable
		}
		Timber.tag("puyo").d("loaded image from url: $url")

		// Get the path of the loaded image from DiskCache.
		val path = imageLoader.diskCache?.get(url)?.use { snapshot ->
			val imageFile = snapshot.data.toFile()
			Timber.tag("puyo").d("image file stored at: ${imageFile.absolutePath}")

			// Use the FileProvider to create a content URI
			val contentUri = getUriForFile(
				context,
				"com.machina.jikan_client_compose.fileprovider",
				imageFile
			)
			Timber.tag("puyo").d("created uri from coil fetched image cache: $contentUri")

			// Find the current launcher everytime to ensure it has read permissions
			val resolveInfo = context.packageManager.resolveActivity(
				Intent(Intent.ACTION_MAIN).apply { addCategory(Intent.CATEGORY_HOME) },
				PackageManager.MATCH_DEFAULT_ONLY
			)

			val launcherName = resolveInfo?.activityInfo?.packageName


			if (launcherName != null) {
				Timber.tag("puyo").d("granting read access for uri: $contentUri | to: $launcherName")
				context.grantUriPermission(
					launcherName,
					contentUri,
					FLAG_GRANT_READ_URI_PERMISSION or FLAG_GRANT_PERSISTABLE_URI_PERMISSION
				)
			}

			// return the path
			imageFile.absolutePath.toString()
		}
		return requireNotNull(path) {
			"Couldn't find cached file"
		}
	}
}