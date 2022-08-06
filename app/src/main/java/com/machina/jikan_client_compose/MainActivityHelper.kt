package com.machina.jikan_client_compose

import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun OnDestinationChanged(
  window: Window,
  systemUiController: SystemUiController = rememberSystemUiController(),
  color: Color = Color.Transparent,
  drawOverStatusBar: Boolean = false
) {
  SideEffect {
    systemUiController.setSystemBarsColor(color = color)
  }

  WindowCompat.setDecorFitsSystemWindows(window, !drawOverStatusBar)
}