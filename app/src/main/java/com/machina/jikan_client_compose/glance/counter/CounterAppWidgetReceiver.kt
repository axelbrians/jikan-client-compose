package com.machina.jikan_client_compose.glance.counter

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.Action
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import timber.log.Timber

class CounterAppWidgetReceiver: GlanceAppWidgetReceiver() {

	override val glanceAppWidget: GlanceAppWidget
		get() {
			Timber.tag("puyo").d("returning new CounterWidget")
			return CounterAppWidget()
		}

	override fun onDeleted(context: Context, appWidgetIds: IntArray) {
		super.onDeleted(context, appWidgetIds)
		Timber.tag("puyo-counter").d("onDeleted - widgetIds: ${appWidgetIds.toList()}")
	}

	override fun onAppWidgetOptionsChanged(
		context: Context,
		appWidgetManager: AppWidgetManager,
		appWidgetId: Int,
		newOptions: Bundle
	) {
		super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
		Timber.tag("puyo-counter").d("onAppWidgetOptionChanged - widgetId: $appWidgetId")
	}

	override fun onReceive(context: Context, intent: Intent) {
		super.onReceive(context, intent)
		Timber.tag("puyo-counter").d("onReceive")
	}

	override fun onUpdate(
		context: Context,
		appWidgetManager: AppWidgetManager,
		appWidgetIds: IntArray
	) {
		super.onUpdate(context, appWidgetManager, appWidgetIds)
		Timber.tag("puyo-counter").d("onUpdate - widgedIds: ${appWidgetIds.toList()}")
	}
}

private class CounterAppWidget: GlanceAppWidget() {

	companion object {
		object CounterPref {
			val CounterKey = intPreferencesKey("jikan-counter")
		}

		object ActionParams {
			private const val KEY_COUNTER = "key_use_case"
			val CounterParameter = ActionParameters.Key<Int>(KEY_COUNTER)
		}
	}

	override val stateDefinition: GlanceStateDefinition<*>
		get() = PreferencesGlanceStateDefinition

	override suspend fun provideGlance(context: Context, id: GlanceId) {
		provideContent {
			// Should not use remember or rememberSaveable because it is not persistent
			// Please refer to GlanceState
			val preference = currentState<Preferences>()
			val counter = preference[CounterPref.CounterKey] ?: 0
			Box(
				modifier = GlanceModifier
					.appWidgetBackground()
					.background(Color.White.copy(alpha = 0.8f))
					.fillMaxSize()
					.padding(16.dp),
				contentAlignment = Alignment.Center
			) {
				CounterContent(
					counter = counter,
					action = { newCounter ->
						actionRunCallback<ActionSetCounter>(
							actionParametersOf(ActionParams.CounterParameter to newCounter)
						)
					},
					setCounter = { newCounter ->
						preference.toMutablePreferences().apply {
							this[CounterPref.CounterKey] = newCounter
						}
					}
				)
			}
		}
	}

	@Composable
	private fun CounterContent(
		counter: Int,
		action: (Int) -> Action,
		setCounter: (Int) -> Unit
	) {
		Column(
			modifier = GlanceModifier.fillMaxWidth(),
			horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
			verticalAlignment = Alignment.Vertical.CenterVertically
		) {
			Text(
				text = counter.toString(),
				style = TextStyle(
					fontSize = 20.sp,
					fontWeight = FontWeight.Medium
				)
			)
			Spacer(modifier = GlanceModifier.height(12.dp))
			Row(
				modifier = GlanceModifier.fillMaxWidth(),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Box(
					modifier = GlanceModifier
						.background(Color.Cyan)
						.size(56.dp)
						.clickable(actionRunCallback<ActionSetCounter>(
							actionParametersOf(ActionParams.CounterParameter to counter - 1)
						))
				) {
					Text(
						text = "-",
	//					onClick = {
	//						setCounter(counter - 1)
	//					},
					)
				}
				Spacer(modifier = GlanceModifier.width(12.dp))
				Button(
					text = "+",
					onClick = {
						setCounter(counter + 1)
					},
					modifier = GlanceModifier.width(56.dp)
				)
			}
		}
	}
}

private class ActionSetCounter: ActionCallback {

	override suspend fun onAction(
		context: Context,
		glanceId: GlanceId,
		parameters: ActionParameters
	) {
		val newCounter = parameters[CounterAppWidget.Companion.ActionParams.CounterParameter]

	    Timber.tag("puyo").d("Counter - onAction with newCounter: $newCounter")

		if (newCounter == null) return

		updateAppWidgetState(context, glanceId) { prefs ->
			prefs[CounterAppWidget.Companion.CounterPref.CounterKey] = newCounter
		}

		CounterAppWidget().update(context, glanceId)
	}
}