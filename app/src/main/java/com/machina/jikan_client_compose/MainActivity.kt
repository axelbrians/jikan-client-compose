package com.machina.jikan_client_compose

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.ui.theme.JikanClientComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	@Inject
	lateinit var dispatchers: DispatchersProvider

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			JikanClientComposeTheme {
				AppScreen(
					window = window,
					modifier = Modifier
						.background(MaterialTheme.colorScheme.background)
				)
			}
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
			splashScreen.setOnExitAnimationListener { splashScreenView ->
				val slideUp = ObjectAnimator.ofFloat(
					splashScreenView,
					View.TRANSLATION_Y,
					0f,
					-splashScreenView.height.toFloat()
				).apply {
					interpolator = AnticipateInterpolator()
					duration = 200L

					// Call SplashScreenView.remove at the end of your custom animation.
					doOnEnd { splashScreenView.remove() }
				}

				// Run your animation.
				slideUp.start()
			}
		}
	}
}

