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
import com.machina.jikan_client_compose.domain.model.anime.AnimeVerticalDataModel
import com.machina.jikan_client_compose.presentation.composable.CenterCircularProgressIndicator
import com.machina.jikan_client_compose.presentation.home_screen.preview.ItemVerticalAnimeProvider
import com.machina.jikan_client_compose.presentation.home_screen.preview.ItemVerticalAnimeState
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyShape
import com.machina.jikan_client_compose.ui.theme.Type

@ExperimentalCoilApi
@Composable
fun ItemVerticalAnime(
	modifier: Modifier = Modifier,
	data: AnimeVerticalDataModel,
	thumbnailHeight: Dp = ItemVerticalAnimeModifier.ThumbnailHeightDefault,
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
			Box(modifier = thumbnailModifier
				.background(MyColor.Teal200)
			)
		} else {
			SubcomposeAsyncImage(
				modifier = thumbnailModifier,
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
fun Preview_ItemVerticalAnime_Default(
	@PreviewParameter(ItemVerticalAnimeProvider::class) data: ItemVerticalAnimeState,
) {
	ItemVerticalAnime(
		modifier = data.modifier,
		data = data.data,
		thumbnailHeight = data.thumbnailHeight,
		onClick = { _, _ -> }
	)
}