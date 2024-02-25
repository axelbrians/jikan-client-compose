package com.machina.jikan_client_compose.presentation.composable

import android.graphics.drawable.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview

object AppBarScope {
	@Composable
	fun Icon() {

	}
}

@Composable
fun DefaultAppBar(
	leadingIcon: @Composable AppBarScope.() -> Unit
) {
	leadingIcon.invoke(AppBarScope)
}

@Preview
@Composable
private fun Preview_Test() {
	DefaultAppBar {
		Text(text = "Super")
		Icon()
	}
}