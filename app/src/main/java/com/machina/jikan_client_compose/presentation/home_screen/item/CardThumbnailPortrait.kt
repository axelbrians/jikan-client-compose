package com.machina.jikan_client_compose.presentation.home_screen.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.SubcomposeAsyncImage
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.domain.model.anime.AnimePortraitDataModel
import com.machina.jikan_client_compose.presentation.composable.CenterCircularLoading
import com.machina.jikan_client_compose.presentation.home_screen.preview.ItemVerticalAnimeProvider
import com.machina.jikan_client_compose.presentation.home_screen.preview.ItemVerticalAnimePreviewParam
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyShape
import com.machina.jikan_client_compose.ui.theme.Type

@ExperimentalCoilApi
@Composable
fun CardThumbnailPortrait(
	imageUrl: String,
	text: String,
	modifier: Modifier = Modifier,
	thumbnailHeight: Dp = CardThumbnailPortraitDefault.Height.Small,
	textAlign: TextAlign = TextAlign.Start,
	onClick: () -> Unit
) {
//  var titleLineCount by remember { mutableStateOf(0) }

	Column(
		modifier = modifier
			.clip(MyShape.Rounded12)
			.clickable(onClick = onClick)
	) {
		val thumbnailModifier = Modifier
			.fillMaxWidth()
			.height(thumbnailHeight)
			.clip(MyShape.Rounded12)
		if (LocalInspectionMode.current) {
			Box(modifier = thumbnailModifier
				.background(MyColor.Teal200)
			)
		} else {
			SubcomposeAsyncImage(
				modifier = thumbnailModifier,
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
		}

		Text(
			text = text,
			modifier = Modifier
				.fillMaxWidth()
				.padding(top = 6.dp, bottom = 4.dp),
			maxLines = 2,
			overflow = TextOverflow.Ellipsis,
			style = Type.Typography.bodyMedium,
			textAlign = textAlign,
//      onTextLayout = {
//        titleLineCount = it.lineCount
//      }
		)

//    repeat(2 - titleLineCount) {
//      Text(
//        text = "",
//        style = MyType.Body2.Bold.OnDarkSurface
//      )
//    }
//
//    Text(
//      text = "${data.score}",
//      modifier = Modifier.padding(bottom = 3.dp),
//      style = TextStyle(
//        color = MyColor.OnDarkSurface,
//        fontSize = 13.sp,
//        fontWeight = FontWeight.Normal
//      )
//    )
	}
}

@ExperimentalCoilApi
@Composable
fun CardThumbnailPortrait(
	modifier: Modifier = Modifier,
	data: AnimePortraitDataModel,
	thumbnailHeight: Dp = CardThumbnailPortraitDefault.Height.Default,
	textAlign: TextAlign = TextAlign.Start,
	onClick: (Int, ContentType) -> Unit
) {
//  var titleLineCount by remember { mutableStateOf(0) }

	Column(
		modifier = modifier
			.clip(MyShape.Rounded12)
			.clickable { onClick(data.malId, ContentType.Anime) }
	) {
		val thumbnailModifier = Modifier
			.fillMaxWidth()
			.height(thumbnailHeight)
			.clip(MyShape.Rounded12)
		if (LocalInspectionMode.current) {
			Box(thumbnailModifier.background(MyColor.Teal200))
		} else {
			SubcomposeAsyncImage(
				modifier = thumbnailModifier,
				model = data.imageUrl,
				contentDescription = "Content thumbnail",
				contentScale = ContentScale.Crop,
				loading = {
					CenterCircularLoading(
						strokeWidth = 2.dp,
						size = 20.dp
					)
				}
			)
		}

		Text(
			text = data.title,
			modifier = Modifier
				.fillMaxWidth()
				.padding(top = 6.dp, bottom = 4.dp),
			maxLines = 2,
			overflow = TextOverflow.Ellipsis,
			style = Type.Typography.bodyMedium,
			textAlign = textAlign,
			color = Color.White
//      onTextLayout = {
//        titleLineCount = it.lineCount
//      }
		)

//    repeat(2 - titleLineCount) {
//      Text(
//        text = "",
//        style = MyType.Body2.Bold.OnDarkSurface
//      )
//    }
//
//    Text(
//      text = "${data.score}",
//      modifier = Modifier.padding(bottom = 3.dp),
//      style = TextStyle(
//        color = MyColor.OnDarkSurface,
//        fontSize = 13.sp,
//        fontWeight = FontWeight.Normal
//      )
//    )
	}
}

@OptIn(ExperimentalCoilApi::class)
@Preview
@Composable
private fun Preview_ItemVerticalAnime_Default(
	@PreviewParameter(ItemVerticalAnimeProvider::class) param: ItemVerticalAnimePreviewParam,
) {
	CardThumbnailPortrait(
		modifier = param.modifier,
		data = param.data,
		thumbnailHeight = param.thumbnailHeight,
		onClick = { _, _ -> }
	)
}