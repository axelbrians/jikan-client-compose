package com.machina.jikan_client_compose.presentation.content_search_screen.composable.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyShape
import com.machina.jikan_client_compose.ui.theme.Type
import com.machina.jikan_client_compose.ui.theme.Type.darkBlue
import com.machina.jikan_client_compose.ui.theme.Type.semiBold
import com.machina.jikan_client_compose.ui.theme.Type.yellow500

@Composable
fun FilterModalBottomSheetPanButtonControl(
	modifier: Modifier = Modifier,
	onReset: () -> Unit,
	onApply: () -> Unit
) {
	Box(
		modifier = modifier
			.padding(top = 8.dp)
			.width(48.dp)
			.height(6.dp)
			.clip(MyShape.RoundedAllPercent50)
			.background(MyColor.Grey)
	)

	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Surface(
			modifier = Modifier
				.clip(MyShape.RoundedAllPercent50)
				.clickable { onReset() },
			color = Color.Transparent,
		) {
			Text(
				text = "Reset",
				style = Type.Typography.bodyLarge.semiBold().yellow500(),
				modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
			)
		}

		Surface(
			modifier = Modifier
				.clip(MyShape.RoundedAllPercent50)
				.clickable(
					interactionSource = remember { MutableInteractionSource() },
					indication = rememberRipple(color = MyColor.Yellow500Ripple),
					onClick = onApply
				),
			color = MyColor.Yellow500,
		) {
			Text(
				text = "Apply",
				style = Type.Typography.bodyLarge.semiBold().darkBlue(),
				modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
			)
		}
	}
}