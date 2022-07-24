package com.machina.jikan_client_compose

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.machina.jikan_client_compose.core.DispatchersProvider
import com.machina.jikan_client_compose.presentation.content_detail_screen.ContentDetailsScreen
import com.machina.jikan_client_compose.presentation.content_search_screen.SearchScreen
import com.machina.jikan_client_compose.presentation.content_view_all_screen.ContentViewAllListScreen
import com.machina.jikan_client_compose.presentation.content_view_all_screen.viewmodel.ContentViewAllAnimeScheduleViewModel
import com.machina.jikan_client_compose.presentation.content_view_all_screen.viewmodel.ContentViewAllAnimeTopViewModel
import com.machina.jikan_client_compose.presentation.content_view_all_screen.viewmodel.ContentViewAllViewModel
import com.machina.jikan_client_compose.presentation.home_screen.HomeScreen
import com.machina.jikan_client_compose.ui.navigation.MainNavigationRoute.CONTENT_DETAILS_SCREEN
import com.machina.jikan_client_compose.ui.navigation.MainNavigationRoute.CONTENT_SEARCH_SCREEN
import com.machina.jikan_client_compose.ui.navigation.MainNavigationRoute.CONTENT_VIEW_ALL_SCREEN
import com.machina.jikan_client_compose.ui.navigation.MainNavigationRoute.HOME_SCREEN
import com.machina.jikan_client_compose.ui.navigation.content_view_all.ContentViewAllType
import com.machina.jikan_client_compose.ui.navigation.navigator.ContentViewAllScreenNavigation
import com.machina.jikan_client_compose.ui.navigation.navigator.HomeScreenNavigation
import com.machina.jikan_client_compose.ui.theme.JikanClientComposeTheme
import com.machina.jikan_client_compose.ui.theme.MyColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  @Inject
  lateinit var dispatchers: DispatchersProvider

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      JikanClientComposeTheme {
        ProvideWindowInsets {
          MyApp(
            window = window,
            dispatchers = dispatchers
          )
        }
      }
    }
  }
}

private val detailsScreenArgs = listOf(
  navArgument(name = "contentType") {
    type = NavType.StringType
    nullable = false
  },
  navArgument(name = "malId") {
    type = NavType.IntType
    nullable = false
  }
)

@OptIn(
  InternalCoroutinesApi::class,
  coil.annotation.ExperimentalCoilApi::class,
  androidx.compose.animation.ExperimentalAnimationApi::class
)
@Composable
fun MyApp(
  modifier: Modifier = Modifier,
  window: Window,
  dispatchers: DispatchersProvider
) {
  val systemUiController = rememberSystemUiController()
  val navController = rememberNavController()
  val homeScrollState = rememberLazyListState()
  NavHost(navController = navController, startDestination = HOME_SCREEN) {
    composable(route = HOME_SCREEN) {
      OnDestinationChanged(
        systemUiController = systemUiController,
        color = MyColor.BlackBackground,
        drawOverStatusBar = false,
        window = window,
      )


      HomeScreen(
        navigation = HomeScreenNavigation(navController),
        viewModel = hiltViewModel(),
        lazyColumnState = homeScrollState,
        onSearchFieldClick = {
          navController.navigate(CONTENT_SEARCH_SCREEN)
        }
      )
    }

    composable(route = CONTENT_SEARCH_SCREEN) { backstack ->
      OnDestinationChanged(
        systemUiController = systemUiController,
        color = MyColor.BlackBackground,
        drawOverStatusBar = false,
        window = window,
      )

      SearchScreen(
        viewModel = hiltViewModel(),
        dispatchers = dispatchers,
        onContentClick = { type, malId ->
          navController.navigate("${CONTENT_DETAILS_SCREEN}/$type/$malId".lowercase())
          Timber.d("navigated with ${CONTENT_DETAILS_SCREEN}/$type/$malId")
        })
    }

    composable(
      route = "$CONTENT_DETAILS_SCREEN/{contentType}/{malId}",
      arguments = detailsScreenArgs
    ) { backStack ->
      OnDestinationChanged(
        systemUiController = systemUiController,
        color = MyColor.Transparent,
        drawOverStatusBar = true,
        window = window,
      )

      ContentDetailsScreen(
        viewModel = hiltViewModel(),
        backStack.arguments?.getString("contentType")?.replaceFirstChar { it.uppercase() },
        backStack.arguments?.getInt("malId"),
        onBackPressed = { navController.navigateUp() }
      )
    }

    composable(
      route = "$CONTENT_VIEW_ALL_SCREEN/{content}",
    ) { backStack ->
      OnDestinationChanged(
        systemUiController = systemUiController,
        color = MyColor.BlackBackground,
        drawOverStatusBar = false,
        window = window,
      )

      val content = ContentViewAllType.valueOf(
        backStack.arguments?.getString("content", "") ?: ""
      )

      val viewModel = when(content) {
        ContentViewAllType.AnimeSchedule -> hiltViewModel<ContentViewAllAnimeScheduleViewModel>()
        ContentViewAllType.AnimeTop -> hiltViewModel<ContentViewAllAnimeTopViewModel>()
        else -> null
      } as? ContentViewAllViewModel

      Timber.d("viewModel: ${viewModel?.javaClass?.simpleName}")

      if (viewModel != null) {
        ContentViewAllListScreen(
          navigation = ContentViewAllScreenNavigation(navController),
          viewModel = viewModel
        )
      } else { // Close screen if content is not supported
        navController.popBackStack()
      }
    }
  }
}

