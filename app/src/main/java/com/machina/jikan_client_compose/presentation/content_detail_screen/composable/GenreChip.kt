package com.machina.jikan_client_compose.presentation.content_detail_screen.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machina.jikan_client_compose.ui.theme.MyColor

@Composable
fun GenreChip(
	modifier: Modifier = Modifier,
	text: String
) {
	Surface(
		modifier = modifier.padding(vertical = 4.dp, horizontal = 4.dp),
		shape = RoundedCornerShape(50),
		color = MyColor.Yellow500,
	) {
		Text(
			text = text,
			style = TextStyle(
				color = MyColor.DarkBlueBackground,
				fontSize = 13.sp,
				fontWeight = FontWeight.SemiBold
			),
			modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
		)
	}
}