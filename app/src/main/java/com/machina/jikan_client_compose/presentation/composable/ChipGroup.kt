package com.machina.jikan_client_compose.presentation.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.core.enums.ContentType
import com.machina.jikan_client_compose.core.enums.ContentType.Anime
import com.machina.jikan_client_compose.core.enums.ContentType.Manga
import com.machina.jikan_client_compose.ui.theme.MyColor

@Preview(showBackground = true)
@Composable
fun ChipGroup(
	modifier: Modifier = Modifier,
	types: List<ContentType> = listOf(Anime, Manga),
	selectedType: ContentType? = null,
	onSelectedChanged: (String) -> Unit = {},
) {
	Column(modifier = Modifier.padding(8.dp)) {
		LazyRow {
			items(types) { type ->
				Chip(
					name = type.name,
					isSelected = selectedType == type,
					onSelectionChanged = { onSelectedChanged(it) },
				)
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun Chip(
	name: String = "Chip",
	isSelected: Boolean = false,
	onSelectionChanged: (String) -> Unit = { },
) {
	Surface(
		modifier = Modifier.padding(horizontal = 4.dp),
		shape = RoundedCornerShape(50),
		border = BorderStroke(
			width = 1.dp,
			color = when {
				isSelected -> MyColor.Yellow500
				else -> MyColor.DarkGreyBackground
			}
		)
	) {
		Row(modifier = Modifier
			.toggleable(
				value = isSelected,
				onValueChange = { onSelectionChanged(name) }
			)
		) {
			val styleSelected = if (isSelected)  {
				TextStyle(color = MyColor.Yellow500, fontWeight = FontWeight.Bold )
			} else {
				TextStyle(color = MyColor.OnDarkSurface, fontWeight = FontWeight.Normal)
			}
			Text (
				text = name,
				style = styleSelected,
				modifier = Modifier.padding(16.dp, 8.dp)
			)
		}
	}
}