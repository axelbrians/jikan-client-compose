package com.machina.jikan_client_compose.presentation.home_screen.composable.anime_popular_current

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerScope
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.machina.jikan_client_compose.domain.model.anime.AnimeAiringPopular
import com.machina.jikan_client_compose.domain.model.anime.AnimeThumbnail
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyShape
import com.machina.jikan_client_compose.ui.theme.Type
import com.machina.jikan_client_compose.ui.theme.Type.bold
import com.machina.jikan_client_compose.ui.theme.Type.onDarkSurface
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerScope.ItemHeadlineCarousel(
	modifier: Modifier = Modifier,
	data: AnimeThumbnail,
	currentPage: Int,
	onClick: () -> Unit
) {
	Surface(
		color = Color.Transparent,
		modifier = Modifier
			.graphicsLayer {
				// Calculate the absolute offset for the current page from the
				// scroll position. We use the absolute value which allows us to mirror
				// any effects for both directions
				val pageOffset = calculateCurrentOffsetForPage(currentPage).absoluteValue
				// We animate the scaleX + scaleY, between 85% and 100%
				lerp(
					start = 0.9f,
					stop = 1f,
					fraction = 1f - pageOffset.coerceIn(0f, 1f)
				).also { scale ->
					scaleX = scale
					scaleY = scale
				}

				// We animate the alpha, between 50% and 100%
				alpha = lerp(
					start = 0.5f,
					stop = 1f,
					fraction = 1f - pageOffset.coerceIn(0f, 1f)
				)
			}
			.fillMaxSize()
			.aspectRatio(1.3f)
	) {
		Box(
			modifier = modifier
				.fillMaxSize()
				.clip(MyShape.Rounded12)
				.clickable { onClick() }
		) {
			SubcomposeAsyncImage(
				modifier = modifier
					.fillMaxSize()
					.clip(MyShape.Rounded12),
				model = data.imageUrl,
				contentDescription = "Content thumbnail",
				contentScale = ContentScale.Crop,
				loading = {
					CenterCircularProgressIndicator(
						strokeWidth = 2.dp,
						size = 20.dp,
						color = MyColor.Yellow500
					)
				}
			)

			/* pager item title */
			Box(
				modifier = Modifier
					.align(Alignment.BottomCenter)
					.fillMaxWidth()
					.background(
						brush = Brush.verticalGradient(
							colors = listOf(
								MyColor.DarkBlueBackground.copy(alpha = 0F),
								MyColor.DarkBlueBackground.copy(alpha = 0.9F),
								MyColor.DarkBlueBackground
							)
						)
					)
					.padding(horizontal = 12.dp, vertical = 6.dp)
					.zIndex(2f)
			) {
				Text(
					text = data.title,
					maxLines = 1,
					textAlign = TextAlign.Center,
					overflow = TextOverflow.Ellipsis,
					style = Type.Typography.bodyMedium.onDarkSurface().bold(),
					modifier = Modifier
						.align(Alignment.BottomCenter)
						.padding(horizontal = 12.dp, vertical = 6.dp),
				)
			}
		}
	}
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerScope.PagerItemAnimeAiringPopular(
	modifier: Modifier = Modifier,
	data: AnimeAiringPopular,
	currentPage: Int,
	onClick: () -> Unit
) {
	Surface(
		color = Color.Transparent,
		modifier = Modifier
			.graphicsLayer {
				// Calculate the absolute offset for the current page from the
				// scroll position. We use the absolute value which allows us to mirror
				// any effects for both directions
				val pageOffset = calculateCurrentOffsetForPage(currentPage).absoluteValue
				// We animate the scaleX + scaleY, between 85% and 100%
				lerp(
					start = 0.9f,
					stop = 1f,
					fraction = 1f - pageOffset.coerceIn(0f, 1f)
				).also { scale ->
					scaleX = scale
					scaleY = scale
				}

				// We animate the alpha, between 50% and 100%
				alpha = lerp(
					start = 0.5f,
					stop = 1f,
					fraction = 1f - pageOffset.coerceIn(0f, 1f)
				)
			}
			.fillMaxSize()
			.aspectRatio(1.3f)
	) {
		Box(
			modifier = modifier
				.fillMaxSize()
				.clip(MyShape.Rounded12)
				.clickable { onClick() }
		) {
			SubcomposeAsyncImage(
				modifier = modifier
					.fillMaxSize()
					.clip(MyShape.Rounded12),
				model = data.imageUrl,
				contentDescription = "Content thumbnail",
				contentScale = ContentScale.Crop,
				loading = {
					CenterCircularProgressIndicator(
						strokeWidth = 2.dp,
						size = 20.dp,
						color = MyColor.Yellow500
					)
				}
			)

			/* pager item title */
			Box(
				modifier = Modifier
					.align(Alignment.BottomCenter)
					.fillMaxWidth()
					.background(
						brush = Brush.verticalGradient(
							colors = listOf(
								MyColor.DarkBlueBackground.copy(alpha = 0F),
								MyColor.DarkBlueBackground.copy(alpha = 0.9F),
								MyColor.DarkBlueBackground
							)
						)
					)
					.padding(horizontal = 12.dp, vertical = 6.dp)
					.zIndex(2f)
			) {
				Text(
					text = data.title,
					maxLines = 1,
					textAlign = TextAlign.Center,
					overflow = TextOverflow.Ellipsis,
					style = Type.Typography.bodyMedium.onDarkSurface().bold(),
					modifier = Modifier
						.align(Alignment.BottomCenter)
						.padding(horizontal = 12.dp, vertical = 6.dp),
				)
			}
		}
	}
}
