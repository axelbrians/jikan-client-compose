package com.machina.jikan_client_compose.presentation.content_search_screen.composable.bottom_sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.machina.jikan_client_compose.ui.theme.JikanTypography
import com.machina.jikan_client_compose.ui.theme.JikanTypography.darkBlue
import com.machina.jikan_client_compose.ui.theme.JikanTypography.semiBold
import com.machina.jikan_client_compose.ui.theme.JikanTypography.yellow500
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyShape

@Composable
fun FilterModalBottomSheetButtonControl(
	modifier: Modifier = Modifier,
	onReset: () -> Unit,
	onApply: () -> Unit
) {
	Row(
		modifier = modifier
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
				style = JikanTypography.JikanTextStyle.bodyLarge.semiBold().yellow500(),
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
				style = JikanTypography.JikanTextStyle.bodyLarge.semiBold().darkBlue(),
				modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
			)
		}
	}
}