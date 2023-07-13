package com.machina.jikan_client_compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.google.accompanist.insets.ProvideWindowInsets

private val DarkColorPalette = darkColorScheme(
	primary = MyColor.Yellow500,
	secondary = MyColor.Yellow500,
	background = MyColor.DarkBlueBackground,
	surface = MyColor.DarkBlueBackground,
	onSurface = MyColor.OnDarkSurface,

)

private val LightColorPalette = lightColorScheme(
	primary = MyColor.Yellow500,
	secondary = MyColor.Yellow500,
	background = MyColor.DarkBlueBackground,
	surface = MyColor.DarkBlueBackground,
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
			colorScheme = colors,
			typography = Type.Typography,
			shapes = MyShape.ThemeShapes,
			content = content
		)
	}
}