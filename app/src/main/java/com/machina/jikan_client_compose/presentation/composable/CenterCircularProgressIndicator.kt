package com.machina.jikan_client_compose.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.ui.theme.JikanClientComposeTheme

@Composable
fun CenterCircularProgressIndicator(
	modifier: Modifier = Modifier,
	size: Dp = 20.dp,
	color: Color = MaterialTheme.colors.primary,
	strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth
) {
	Box(
		modifier = modifier
		.fillMaxSize()
		.background(MaterialTheme.colors.background)
	) {
		CircularProgressIndicator(
			modifier = Modifier
				.size(size)
				.align(Alignment.Center),
			color = color,
			strokeWidth = strokeWidth
		)
	}
}

@Preview(widthDp = 200, heightDp = 200)
@Composable
fun Preview_CenterCircularProgressIndicator() {
	JikanClientComposeTheme {
		CenterCircularProgressIndicator()
	}
}