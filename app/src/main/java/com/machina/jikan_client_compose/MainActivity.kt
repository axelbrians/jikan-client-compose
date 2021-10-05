package com.machina.jikan_client_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.ui.navigation.MainNavigation.ANIME_DETAILS_SCREEN
import com.machina.jikan_client_compose.ui.navigation.MainNavigation.HOME_SCREEN
import com.machina.jikan_client_compose.ui.theme.JikanclientcomposeTheme
import com.machina.jikan_client_compose.view.detail_screen.DetailAnimeScreen
import com.machina.jikan_client_compose.view.home_screen.HomeScreen
import com.machina.jikan_client_compose.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalCoilApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JikanclientcomposeTheme {
                val navController = rememberNavController()


                NavHost(navController = navController, startDestination = HOME_SCREEN) {
                    composable(HOME_SCREEN) {
                        val homeViewModel = hiltViewModel<HomeViewModel>()
                        HomeScreen(
                            navController = navController,
                            viewModel = homeViewModel
                        )
                    }
                    composable(ANIME_DETAILS_SCREEN) {
                        DetailAnimeScreen()
                    }
                }
            }
        }
    }
}