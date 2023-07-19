package com.machina.jikan_client_compose.presentation.content_search_screen.composable.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.ui.theme.Type
import com.machina.jikan_client_compose.ui.theme.Type.onDarkSurface

@Composable
fun FilterCheckable(
	modifier: Modifier = Modifier,
	text: String = "",
	isChecked: Boolean = false,
	onCheck: () -> Unit = { }
) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.clickable { onCheck() }
			.padding(horizontal = 16.dp, vertical = 12.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Checkbox(
			checked = isChecked,
			onCheckedChange = null,
			modifier = Modifier.padding(end = 12.dp)
		)

		Text(
			text = text,
			style = Type.Typography.bodyLarge.onDarkSurface()
		)
	}
}