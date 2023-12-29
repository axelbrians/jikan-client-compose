package com.machina.jikan_client_compose.presentation.content_detail_screen.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.machina.jikan_client_compose.data.remote.dto.common.Jpg.Companion.getHighestResImgUrl
import com.machina.jikan_client_compose.presentation.composable.CenterCircularLoading
import com.machina.jikan_client_compose.presentation.content_detail_screen.data.ContentDetailsState
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyIcons
import com.machina.jikan_client_compose.ui.theme.MyShape
import com.machina.jikan_client_compose.ui.theme.MySize

@ExperimentalCoilApi
@Composable
fun ContentDetailsHeader(
	contentDetailsState: ContentDetailsState,
	modifier: Modifier = Modifier
) {
	val blockerColorGradients = listOf(
		MyColor.DarkBlueBackground.copy(alpha = 0.8F),
		MyColor.DarkBlueBackground.copy(alpha = 0.9F),
		MyColor.DarkBlueBackground
	)

	val (
		headerCaptionIcon: ImageVector,
		headerCaptionDescription: String
	) = resolveHeaderIconAndDescription(contentDetailsState)

	val imageUrl by remember(contentDetailsState.data?.images?.jpg) {
		mutableStateOf(contentDetailsState.data?.images?.jpg?.getHighestResImgUrl())
	}

	// Parallax header background
	Box(modifier = modifier.fillMaxWidth().heightIn(max = 240.dp)) {
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
					CenterCircularLoading(
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

				contentDetailsState.data?.let { data ->
					data.authors.firstOrNull()?.let { author ->
						Text(
							text = author.name,
							style = TextStyle(
								color = MyColor.OnDarkSurface,
								fontWeight = FontWeight.Bold,
								fontSize = MySize.Text14
							)
						)
					}

					data.studios.firstOrNull()?.let { studio ->
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

@Composable
fun resolveHeaderIconAndDescription(
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