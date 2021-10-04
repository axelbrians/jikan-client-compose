package com.machina.jikan_client_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.machina.jikan_client_compose.ui.theme.JikanclientcomposeTheme
import com.machina.jikan_client_compose.ui.view.home_screen.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JikanclientcomposeTheme {
                val navController = rememberNavController()


                NavHost(navController = navController, startDestination = "home_screen") {
                    composable("home_screen") { HomeScreen() }
                    composable("detail_screen") {  }
                }
            }
        }
    }
}