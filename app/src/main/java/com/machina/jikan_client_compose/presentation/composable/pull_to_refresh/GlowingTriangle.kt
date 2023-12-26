package com.machina.jikan_client_compose.presentation.composable.pull_to_refresh

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun GlowingTriangle(
	progress: Float,
	isRefreshing: Boolean,
	modifier: Modifier = Modifier
) {
	val triangleGlow by animateFloatAsState(
		targetValue = when {
			progress > 1f || isRefreshing -> 10f
			else -> 5f
		},
		animationSpec = spring(stiffness = Spring.StiffnessLow),
		label = "triangleGlow",
	)

	Canvas(
		modifier = modifier
			.clip(TriangleShape)
			.blur(4.dp)
			.fillMaxSize()

	) {
		val triangle = size.createTrianglePath()
		drawPath(
			path = triangle,
			color = Color.Black,
		)

		drawPath(
			path = triangle,
			color = Color.White,
			style = Stroke(
				width = triangleGlow,
			)
		)
	}
}

private val TriangleShape = Triangle()

private class Triangle : Shape {
	override fun createOutline(
		size: Size,
		layoutDirection: LayoutDirection,
		density: Density
	): Outline = Outline.Generic(size.createTrianglePath())
}

private fun Size.createTrianglePath(): Path {
	val triangle = Path()
	triangle.apply {
		moveTo(center.x - 100f, center.y + 100f)
		lineTo(center.x, center.y - 100f)
		lineTo(center.x + 100f, center.y + 100f)
		close()
	}
	return triangle
}