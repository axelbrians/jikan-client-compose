package com.machina.jikan_client_compose.presentation.home_screen.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.core.constant.Constant
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault.Height
import com.machina.jikan_client_compose.presentation.home_screen.item.CardThumbnailPortraitDefault.Width
import com.machina.jikan_client_compose.presentation.home_screen.preview.ItemVerticalAnimeMoreParameterProvider
import com.machina.jikan_client_compose.presentation.home_screen.preview.ItemVerticalAnimeMoreState
import com.machina.jikan_client_compose.ui.theme.JikanTypography
import com.machina.jikan_client_compose.ui.theme.JikanTypography.alignCenter
import com.machina.jikan_client_compose.ui.theme.JikanTypography.bold
import com.machina.jikan_client_compose.ui.theme.JikanTypography.onDarkSurface
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyIcons
import com.machina.jikan_client_compose.ui.theme.MyShape

@Composable
fun ItemVerticalAnimeMore(
	modifier: Modifier = Modifier,
	thumbnailHeight: Dp = Height.Default,
	onClick: () -> Unit
) {
	Column(
		modifier = modifier
			.clip(MyShape.Rounded12)
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.height(thumbnailHeight)
				.clip(MyShape.Rounded12)
				.background(MyColor.DarkGreyBackground)
				.clickable { onClick() },
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center,
		) {
			Icon(
				imageVector = ImageVector.vectorResource(id = MyIcons.Filled.ChevronDown),
				contentDescription = "",
				tint = MyColor.OnDarkSurface,
				modifier = Modifier
					.size(32.dp)
					.padding(bottom = 6.dp)
					.rotate(-90f)
			)
			Text(
				text = "See all",
				style = JikanTypography.JikanTextStyle.bodyMedium.bold().onDarkSurface().alignCenter(),
				modifier = Modifier.fillMaxWidth()
			)
		}
	}
}

fun LazyListScope.showItemVerticalAnimeMoreWhenPastLimit(
	modifier: Modifier = Modifier.width(Width.Default),
	thumbnailHeight: Dp = Height.Default,
	limit: Int = Constant.HORIZONTAL_CONTENT_LIMIT,
	size: Int = 0,
	onClick: () -> Unit
) {
	if (size > limit) {
		item {
			ItemVerticalAnimeMore(
				modifier = modifier,
				thumbnailHeight = thumbnailHeight,
				onClick = onClick
			)
		}
	}
}

@Preview
@Composable
fun Preview_ItemVerticalAnimeMore(
	@PreviewParameter(ItemVerticalAnimeMoreParameterProvider::class) state: ItemVerticalAnimeMoreState
) {
	ItemVerticalAnimeMore(
		modifier = state.modifier,
		thumbnailHeight = state.thumbnailHeight,
		onClick = { }
	)
}