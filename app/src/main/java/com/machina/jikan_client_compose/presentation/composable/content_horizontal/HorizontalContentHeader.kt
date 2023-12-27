package com.machina.jikan_client_compose.presentation.composable.content_horizontal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyShape

object HorizontalContentHeaderDefaults {
	val Default = Modifier
		.fillMaxWidth()
		.padding(start = 18.dp, end = 12.dp, bottom = 4.dp)
	val fillWidth = Modifier.fillMaxWidth()

	@Composable
	fun Placeholder(modifier: Modifier = Modifier) {
		HorizontalContentHeader(
			title = "",
			modifier = modifier
				.fillMaxWidth()
				.placeholder(
					visible = true,
					color = MyColor.Grey,
					shape = MyShape.ThemeShapes.extraSmall,
					highlight = PlaceholderHighlight.fade()
				)
		)
	}
}

@Composable
fun HorizontalContentHeader(
	title: String,
	modifier: Modifier = Modifier,
	onButtonClick: (() -> Unit)? = null
) {
	Row(
		modifier = modifier.heightIn(min = 32.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Text(
			text = title,
			style = TextStyle(
				color = MyColor.Yellow500,
				fontWeight = FontWeight.Bold,
				fontSize = 16.sp
			)
		)

		if (onButtonClick != null) {
			IconButton(onClick = onButtonClick) {
				Icon(
					imageVector = Icons.AutoMirrored.Filled.ArrowForward,
					contentDescription = "see all",
					tint = MyColor.Grey
				)
			}
		}
	}
}