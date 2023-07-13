package com.machina.jikan_client_compose

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.animation.doOnEnd
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.presentation.NavGraphs
import com.machina.jikan_client_compose.ui.theme.JikanClientComposeTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
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
//        MyApp(
//          window = window,
//          dispatchers = dispatchers
//
				DestinationsNavHost(
					navGraph = NavGraphs.root,
					dependenciesContainerBuilder = {
						dependency(window)
						dependency(rememberSystemUiController())
					}
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
				)
				slideUp.interpolator = AnticipateInterpolator()
				slideUp.duration = 200L

				// Call SplashScreenView.remove at the end of your custom animation.
				slideUp.doOnEnd { splashScreenView.remove() }

				// Run your animation.
				slideUp.start()
			}
		}
	}
}

//@OptIn(
//  coil.annotation.ExperimentalCoilApi::class,
//  androidx.compose.animation.ExperimentalAnimationApi::class
//)
//@Composable
//fun MyApp(
//  window: Window,
//  dispatchers: DispatchersProvider
//) {
//  val systemUiController = rememberSystemUiController()
//  val navController = rememberNavController()
//
//  NavHost(navController = navController, startDestination = HOME_SCREEN) {
//    homeScreenNav(
//      systemUiController = systemUiController,
//      window = window,
//      navController = navController
//    )
//
//    searchNav(
//      systemUiController = systemUiController,
//      window = window,
//      navController = navController,
//      dispatchers = dispatchers
//    )
//
//    contentDetailsNav(
//      systemUiController = systemUiController,
//      window = window,
//      navController = navController
//    )
//
//    contentViewAllListNav(
//      systemUiController = systemUiController,
//      window = window,
//      navController = navController
//    )
//  }
//}