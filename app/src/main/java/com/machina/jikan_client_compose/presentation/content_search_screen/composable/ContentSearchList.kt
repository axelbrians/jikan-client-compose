package com.machina.jikan_client_compose.presentation.content_search_screen.composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.shimmer.ItemAnimeSearchShimmer
import com.machina.jikan_client_compose.presentation.content_search_screen.data.ContentSearchState
import com.machina.jikan_client_compose.presentation.content_search_screen.item.ItemAnimeSearch
import com.machina.jikan_client_compose.ui.shimmer.onUpdateShimmerBounds
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

fun Modifier.simpleVerticalScrollbar(
	state: LazyListState,
	width: Dp = 8.dp
): Modifier = composed {
	val targetAlpha = if (state.isScrollInProgress) 1f else 0f
	val duration = if (state.isScrollInProgress) 150 else 500

	val alpha by animateFloatAsState(
		targetValue = targetAlpha,
		animationSpec = tween(durationMillis = duration),
		label = "Scrollbar Alpha"
	)

	drawWithContent {
		drawContent()

		val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index
		val needDrawScrollbar = state.isScrollInProgress || alpha > 0.0f

		// Draw scrollbar if scrolling or if the animation is still running and lazy column has content
		if (needDrawScrollbar && firstVisibleElementIndex != null) {
			val elementHeight = this.size.height / state.layoutInfo.totalItemsCount
			val scrollbarOffsetY = firstVisibleElementIndex * elementHeight
			val scrollbarHeight = state.layoutInfo.visibleItemsInfo.size * elementHeight

			drawRect(
				color = Color.Red,
				topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
				size = Size(width.toPx(), scrollbarHeight),
				alpha = alpha
			)
		}
	}
}

@ExperimentalCoilApi
@Composable
fun ContentSearchList(
	state: ContentSearchState,
	onItemClick: (Int, ContentType) -> Unit,
	modifier: Modifier = Modifier,
	listState: LazyListState = rememberLazyListState(),
) {
	val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Custom)
	val error by produceState<String?>(initialValue = null) {
		value = state.error.peekContent()
	}

	LazyColumn(
		modifier = modifier
			.onUpdateShimmerBounds(shimmerInstance)
			.simpleVerticalScrollbar(listState),
		state = listState,
	) {
		itemsIndexed(
			items = state.data.data,
			key = { _, data -> "${data.malId}" }
		) { _, data ->
//      when (data.type) {
//        MANGA, MANHUA, MANHWA, DOUJIN, ONE_SHOT, LIGHT_NOVEL -> {
//          ItemMangaSearch(
//            modifier = Modifier
//              .fillMaxWidth()
//              .padding(12.dp, 8.dp),
//            data = data,
//            onItemClick = onItemClick
//          )
//        }
//        else -> {
			ItemAnimeSearch(
				modifier = Modifier,
				data = data,
				onItemClick = onItemClick
			)
//        }
//      }

		}

		if (state.isLoading) {
			items(4) {
				ItemAnimeSearchShimmer(shimmerInstance)
			}
		}

		error?.let {
			item {
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.padding(horizontal = 24.dp)
				) {
					Text(
						text = it,
						color = MyColor.OnDarkSurface,
						fontSize = 20.sp,
						textAlign = TextAlign.Center,
						modifier = Modifier.fillMaxWidth()
					)
				}
			}
		}
	}
}