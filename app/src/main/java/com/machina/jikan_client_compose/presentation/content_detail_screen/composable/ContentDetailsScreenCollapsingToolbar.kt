package com.machina.jikan_client_compose.presentation.content_detail_screen.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.insets.statusBarsPadding
import com.machina.jikan_client_compose.data.remote.dto.common.Jpg.Companion.getHighestResImgUrl
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.presentation.content_detail_screen.data.ContentDetailsState
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyIcons
import com.machina.jikan_client_compose.ui.theme.MyShape
import com.machina.jikan_client_compose.ui.theme.MySize
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.CollapsingToolbarScope
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import kotlin.math.roundToInt

@ExperimentalCoilApi
@Composable
fun CollapsingToolbarScope.ContentDetailsScreenToolbar(
	contentDetailsState: ContentDetailsState = ContentDetailsState(null),
	toolbarScaffoldState: CollapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState(),
	onArrowClick: () -> Boolean = { false }
) {
	val blockerColorGradients = listOf(
		MyColor.DarkBlueBackground.copy(alpha = 0.8F),
		MyColor.DarkBlueBackground.copy(alpha = 0.9F),
		MyColor.DarkBlueBackground
	)

	val isTitleVisible = toolbarScaffoldState.toolbarState.progress <= 0.25
	val imageUrl = contentDetailsState.data?.images?.jpg?.getHighestResImgUrl()

	val (
		headerCaptionIcon: ImageVector,
		headerCaptionDescription: String
	) = resolveHeaderIconAndDescription(contentDetailsState)

	Box(
		modifier = Modifier
			.fillMaxWidth()
			.height(240.dp)
			.parallax(0.5f)
			.graphicsLayer {
				// change alpha of Image as the toolbar expands
				alpha = toolbarScaffoldState.toolbarState.progress
			},
	) {
		// Toolbar
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.statusBarsPadding(),
			verticalAlignment = Alignment.CenterVertically
		) {
			IconButton(onClick = { onArrowClick() }) {
				Icon(
					imageVector = Icons.Default.ArrowBack,
					contentDescription = "Back",
					tint = MyColor.OnDarkSurfaceLight
				)
			}

			val density = LocalDensity.current
			val initialOffset = with(density) {
				40.dp.toPx().roundToInt()
			}
			val targetOffset = with(density) {
				-40.dp.toPx().roundToInt()
			}

			AnimatedVisibility(
				visible = isTitleVisible,
				enter = slideInVertically(
					initialOffsetY = { initialOffset },
					animationSpec = tween(
						durationMillis = 800,
						delayMillis = 50,
						easing = FastOutSlowInEasing
					)
				) + fadeIn(initialAlpha = 0f),
				exit = slideOutVertically(
					targetOffsetY = { targetOffset },
					animationSpec = tween(
						durationMillis = 800,
						delayMillis = 50,
						easing = LinearOutSlowInEasing
					)
				) + fadeOut()
			) {
				Text(
					text = contentDetailsState.data?.title ?: "-",
					overflow = TextOverflow.Ellipsis,
					maxLines = 1,
					style = TextStyle(
						color = MyColor.OnDarkSurfaceLight,
						fontWeight = FontWeight.Bold,
						fontSize = 20.sp
					),
					modifier = Modifier
						.weight(1f)
						.padding(start = 8.dp, end = 12.dp)
				)
			}
		}


		// Parallax header background
		Box {
			AsyncImage(
				model = imageUrl,
				contentDescription = "Heading Background",
				contentScale = ContentScale.Crop,
				modifier = Modifier
					.fillMaxSize()
			)
			Box(
				modifier = Modifier
					.fillMaxSize()
					.background(
						brush = Brush.verticalGradient(colors = blockerColorGradients)
					)
			)

			// Header Content
			Row(
				modifier = Modifier
					.fillMaxSize()
					.statusBarsPadding()
					.padding(top = 52.dp, start = 16.dp, end = 16.dp, bottom = 12.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				// Left cover image
				SubcomposeAsyncImage(
					modifier = Modifier
						.width(100.dp)
						.fillMaxHeight()
						.clip(MyShape.Rounded12),
					model = imageUrl,
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

				// Header right content
				Column(
					modifier = Modifier
						.fillMaxWidth()
						.padding(start = 16.dp)
				) {
					Text(
						text = contentDetailsState.data?.title ?: "-",
						style = TextStyle(
							color = MyColor.OnDarkSurfaceLight,
							fontWeight = FontWeight.Bold,
							fontSize = MySize.Text20
						)
					)

					with(contentDetailsState.data) {
						this?.authors?.firstOrNull()?.let { author ->
							Text(
								text = author.name,
								style = TextStyle(
									color = MyColor.OnDarkSurface,
									fontWeight = FontWeight.Bold,
									fontSize = MySize.Text14
								)
							)
						}

						this?.studios?.firstOrNull()?.let { studio ->
							Text(
								text = studio.name,
								style = TextStyle(
									color = MyColor.OnDarkSurface,
									fontWeight = FontWeight.Bold,
									fontSize = MySize.Text14
								)
							)
						}
					}

					// Ongoing / Airing status
					Row(verticalAlignment = Alignment.CenterVertically) {

						Icon(
							imageVector = headerCaptionIcon,
							contentDescription = headerCaptionDescription,
							tint = MyColor.OnDarkSurface,
							modifier = Modifier
								.height(14.dp)
								.padding(end = 6.dp))

						Text(
							text = contentDetailsState.data?.status ?: "-",
							style = TextStyle(
								color = MyColor.OnDarkSurface,
								fontWeight = FontWeight.Bold,
								fontSize = 13.sp
							)
						)
					}
				}
			}
		}
	}
}

@Composable
private fun resolveHeaderIconAndDescription(
	data: ContentDetailsState
): Pair<ImageVector, String> {
	return if (
		data.data?.isAiring == true ||
		data.data?.isPublishing == true
	) {
		ImageVector.vectorResource(id = MyIcons.Outlined.Clock4) to
		"Ongoing"
	} else {
		ImageVector.vectorResource(id = MyIcons.Outlined.DoubleCheck) to
		"Completed"
	}

}