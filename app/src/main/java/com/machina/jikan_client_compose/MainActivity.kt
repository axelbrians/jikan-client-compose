package com.machina.jikan_client_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.ui.navigation.MainNavigation.CONTENT_DETAILS_SCREEN
import com.machina.jikan_client_compose.ui.navigation.MainNavigation.HOME_SCREEN
import com.machina.jikan_client_compose.ui.theme.JikanclientcomposeTheme
import com.machina.jikan_client_compose.presentation.detail_screen.ContentDetailsScreen
import com.machina.jikan_client_compose.presentation.home_screen.HomeScreen
import com.machina.jikan_client_compose.presentation.detail_screen.data.ContentDetailsViewModel
import com.machina.jikan_client_compose.presentation.home_screen.data.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import timber.log.Timber

@AndroidEntryPoint
@ExperimentalCoilApi
class MainActivity : ComponentActivity() {

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

  @InternalCoroutinesApi
  @ExperimentalAnimationApi
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      JikanclientcomposeTheme {
        val navController = rememberNavController()
        val homeScrollState = rememberLazyListState()

        NavHost(navController = navController, startDestination = HOME_SCREEN) {

          composable(HOME_SCREEN) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
              navController = navController,
              viewModel = homeViewModel,
              lazyColumnState = homeScrollState,
              onContentClick = { type, malId ->
                navController.navigate("${CONTENT_DETAILS_SCREEN}/$type/$malId".lowercase())
                Timber.d("navigated with ${CONTENT_DETAILS_SCREEN}/$type/$malId")
              }
            )
          }

          composable(
            "$CONTENT_DETAILS_SCREEN/{contentType}/{malId}",
            arguments = detailsScreenArgs
          ) { backStack ->
            val detailsViewModel = hiltViewModel<ContentDetailsViewModel>()
            Timber.d("contentType ${backStack.arguments?.getString("contentType")}")
            ContentDetailsScreen(
              viewModel = detailsViewModel,
              backStack.arguments?.getString("contentType")?.replaceFirstChar { it.uppercase() },
              backStack.arguments?.getInt("malId"),
              onBackPressed = { navController.navigateUp() }
            )
          }
        }
      }
    }
  }
}