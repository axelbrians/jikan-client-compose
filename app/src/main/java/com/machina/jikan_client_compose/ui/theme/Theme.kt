package com.machina.jikan_client_compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.insets.ProvideWindowInsets

private val DarkColorPalette = darkColors(
  primary = MyColor.Yellow500,
  primaryVariant = MyColor.Yellow500,
  secondary = MyColor.Yellow500,
  background = MyColor.DarkBlueBackground,
  onSurface = MyColor.OnDarkSurface
)

private val LightColorPalette = lightColors(
  primary = MyColor.Yellow500,
  primaryVariant = MyColor.Yellow500,
  secondary = MyColor.Yellow500,
  background = MyColor.DarkBlueBackground,
  onSurface = MyColor.OnDarkSurface,


  /* Other default colors to override
  background = Color.White,
  surface = Color.White,
  onPrimary = Color.White,
  onSecondary = Color.Black,
  onBackground = Color.Black,
  onSurface = Color.Black,
  */
)

@Composable
fun JikanClientComposeTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable() () -> Unit
) {
//    val colors = if (darkTheme) {
//        DarkColorPalette
//    } else {
//        LightColorPalette
//    }

  val colors = DarkColorPalette
  ProvideWindowInsets {
    MaterialTheme(
      colors = colors,
      typography = Type.Typography,
      shapes = MyShape.ThemeShapes,
      content = content
    )
  }
}