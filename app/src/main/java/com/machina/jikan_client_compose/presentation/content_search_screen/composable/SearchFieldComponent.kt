package com.machina.jikan_client_compose.presentation.content_search_screen.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.R
import com.machina.jikan_client_compose.core.constant.Constant
import com.machina.jikan_client_compose.presentation.composable.TextFieldScaffold
import com.machina.jikan_client_compose.ui.theme.JikanTypography
import com.machina.jikan_client_compose.ui.theme.JikanTypography.grey
import com.machina.jikan_client_compose.ui.theme.JikanTypography.onDarkSurface
import com.machina.jikan_client_compose.ui.theme.MyColor

@Composable
fun SearchFieldComponent(
	value: String,
	modifier: Modifier = Modifier,
	isEnabled: Boolean = true,
	placeholder: String = Constant.SEARCH_FIELD_PLACEHOLDER,
	focusRequester: FocusRequester = FocusRequester(),
	onValueChanged: (String) -> Unit,
	onValueCleared: () -> Unit
) {
	TextFieldScaffold(
		modifier = modifier,
		padding = PaddingValues(12.dp),
		leadingIcon = {
			SearchLeadingIcon(
				size = 16.dp,
				padding = PaddingValues(end = 8.dp)
			)
		},
		trailingIcon = {
			if (value.isNotEmpty()) {
				SearchTrailingIcon(
					size = 16.dp,
					padding = PaddingValues(start = 8.dp),
					onClick = onValueCleared
				)
			}
		}
	) {
		if (isEnabled) {
			BasicTextField(
				modifier = Modifier
					.fillMaxWidth()
					.focusRequester(focusRequester),
				value = value,
				textStyle = JikanTypography.JikanTextStyle.titleMedium.onDarkSurface(),
				singleLine = true,
				cursorBrush = SolidColor(MyColor.Yellow500),
				onValueChange = { onValueChanged(it) },
			)
		}

		if (value.isEmpty()) {
			Text(
				text = placeholder,
				style = JikanTypography.JikanTextStyle.titleMedium.grey()
			)
		}
	}
}

@Composable
fun SearchLeadingIcon(
	size: Dp = 24.dp,
	padding: PaddingValues = PaddingValues(6.dp)
) {
	Icon(
		imageVector = Icons.Default.Search,
		contentDescription = "Search",
		modifier = Modifier.padding(padding),
		tint = MyColor.Grey
	)
}

@Composable
fun SearchTrailingIcon(
	size: Dp = 24.dp,
	padding: PaddingValues = PaddingValues(6.dp),
	onClick: () -> Unit
) {
	IconButton(
		modifier = Modifier.then(Modifier.size(size)),
		onClick = onClick,
	) {
		Icon(
			painter = painterResource(R.drawable.ic_close),
			contentDescription = "Close",
			tint = MyColor.Grey
		)
	}
}

@Preview(widthDp = 240)
@Composable
private fun Preview_SearchFieldComponent() {
	SearchFieldComponent(
		value = "",
		onValueChanged = { },
		onValueCleared = { }
	)
}
