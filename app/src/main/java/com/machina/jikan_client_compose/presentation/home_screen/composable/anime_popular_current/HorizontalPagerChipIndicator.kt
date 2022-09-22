package com.machina.jikan_client_compose.presentation.home_screen.composable.anime_popular_current

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyShape

@Composable
fun HorizontalPagerChipIndicator(
	modifier: Modifier = Modifier,
	minWidth: Dp = 8.dp,
	maxWidth: Dp = 24.dp,
	itemIndex: Int,
	currentPage: Int,
	targetPage: Int,
	currentPageOffset: Float,
	targetPageOffset: Float
) {
	val rangeWidth = maxWidth - minWidth

	val animateWidth = when(itemIndex) {
		currentPage -> minWidth + (rangeWidth * (1f - currentPageOffset.coerceIn(0f, 1f)))
		targetPage -> minWidth + (rangeWidth * (1f - targetPageOffset.coerceIn(0f, 1f)))
		else -> minWidth
	}
	val animateColor = animateColorAsState(
		targetValue = if (itemIndex == currentPage) {
			MyColor.Yellow500
		} else {
			MyColor.Grey
		},
		animationSpec = tween(
			durationMillis = 400,
			easing = LinearOutSlowInEasing
		)
	)

	Box(
		modifier = Modifier
			.padding(horizontal = 2.dp)
			.width(animateWidth)
			.height(8.dp)
			.clip(MyShape.RoundedAllPercent50)
			.background(animateColor.value)
	)
}