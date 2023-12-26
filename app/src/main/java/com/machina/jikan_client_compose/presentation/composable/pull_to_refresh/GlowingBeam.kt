package com.machina.jikan_client_compose.presentation.composable.pull_to_refresh

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlowingBeamLoadingIndicator(
	pullToRefreshState: PullToRefreshState,
	isRefreshing: Boolean,
	maxHeight: Int,
	modifier: Modifier = Modifier
) {
	val maxHeightInDp = maxHeight.dp
	val loadingIndicatorContainerOffset by animateDpAsState(
		targetValue = when {
			isRefreshing -> maxHeightInDp

			pullToRefreshState.progress in 0f..1f -> {
				(pullToRefreshState.progress * maxHeight).dp
			}

			pullToRefreshState.progress > 1f -> {
				(maxHeight + (
				  ((pullToRefreshState.progress - 1f) * .1f) * maxHeight
		        )).dp
			}

			else -> 0.dp
		}, label = "containerOffset"
	)

	Box(
		modifier = modifier
			.height(maxHeightInDp)
			.offset(y = -maxHeightInDp)
			.offset {
				IntOffset(0, loadingIndicatorContainerOffset.roundToPx())
			}
	) {
	    GlowingBeam(
		    progress = pullToRefreshState.progress,
		    isRefreshing = isRefreshing
	    )
    }
}

@Composable
fun GlowingBeam(
	progress: Float,
	isRefreshing: Boolean,
	modifier: Modifier = Modifier
) {
	val xDiff = 30f
	val yDiff = 25f
	val beamLength by animateFloatAsState(
		targetValue = if (isRefreshing) {
			1f
		} else {
			progress
		}, label = "beamLength"
	)
	val beamGlow by animateDpAsState(
		targetValue = if (progress > 1f || isRefreshing) {
			8.dp
		} else {
			2.dp
		}, label = "beamGlow"
	)
	val beamGlowAlpha by animateFloatAsState(
		targetValue = if (progress > 1f || isRefreshing) {
			0.4f
		} else {
			0.1f
		}, label = "beamGlowAlpha"
	)


	// Glow effect
	Canvas(
		modifier = modifier
			.blur(beamGlow, BlurredEdgeTreatment.Unbounded)
			.fillMaxSize()
	) {
		val line = Path()
		line.apply {
			moveTo(0f - xDiff, size.center.y + yDiff)
			lineTo(size.width, size.center.y - yDiff)
		}

		val linePathMeasure = PathMeasure()
		linePathMeasure.setPath(line, false)

		drawPath(
			path = line,
			color = Color.White,
			alpha = beamGlowAlpha,
			style = Stroke(
				width = 30f,
				cap = StrokeCap.Round,
				pathEffect = PathEffect.dashPathEffect(
					intervals = floatArrayOf(
						linePathMeasure.length * beamLength,
						linePathMeasure.length
					)
				)
			)
		)
	}

	// Actual beam line
	Canvas(modifier = Modifier.fillMaxSize()) {
		val line = Path()
		line.apply {
			moveTo(0f - xDiff, size.center.y + yDiff)
			lineTo(size.width, size.center.y - yDiff)
		}

		val linePathMeasure = PathMeasure()
		linePathMeasure.setPath(line, false)

		drawPath(
			path = line,
			color = Color.White,
			alpha = .06f,
			style = Stroke(
				width = 30f,
				cap = StrokeCap.Round,
				pathEffect = PathEffect.dashPathEffect(
					intervals = floatArrayOf(
						linePathMeasure.length * beamLength,
						linePathMeasure.length
					)
				)
			)
		)

		drawPath(
			path = line,
			color = Color.White,
			style = Stroke(
				width = 5f,
				pathEffect = PathEffect.dashPathEffect(
					intervals = floatArrayOf(
						linePathMeasure.length * beamLength,
						linePathMeasure.length
					)
				)
			)
		)
	}
}