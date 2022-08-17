package com.machina.jikan_client_compose

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.animation.doOnEnd
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.presentation.content_detail_screen.contentDetailsNav
import com.machina.jikan_client_compose.presentation.content_search_screen.searchNav
import com.machina.jikan_client_compose.presentation.content_view_all_screen.contentViewAllListNav
import com.machina.jikan_client_compose.presentation.home_screen.homeScreenNav
import com.machina.jikan_client_compose.ui.navigation.MainNavigationRoute.HOME_SCREEN
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
        MyApp(
          window = window,
          dispatchers = dispatchers
        )
      }
    }

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

@OptIn(
  coil.annotation.ExperimentalCoilApi::class,
  androidx.compose.animation.ExperimentalAnimationApi::class
)
@Composable
fun MyApp(
  window: Window,
  dispatchers: DispatchersProvider
) {
  val systemUiController = rememberSystemUiController()
  val navController = rememberNavController()

  NavHost(navController = navController, startDestination = HOME_SCREEN) {
    homeScreenNav(
      systemUiController = systemUiController,
      window = window,
      navController = navController
    )

    searchNav(
      systemUiController = systemUiController,
      window = window,
      navController = navController,
      dispatchers = dispatchers
    )

    contentDetailsNav(
      systemUiController = systemUiController,
      window = window,
      navController = navController
    )

    contentViewAllListNav(
      systemUiController = systemUiController,
      window = window,
      navController = navController
    )
  }
}

