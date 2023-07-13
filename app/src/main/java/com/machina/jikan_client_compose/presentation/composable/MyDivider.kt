package com.machina.jikan_client_compose.presentation.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.ui.theme.MyColor

object MyDivider {

	object Horizontal {
		@Composable
		fun DarkGreyBackground(padding: PaddingValues = PaddingValues(0.dp)) {
			Divider(
				modifier = Modifier.fillMaxWidth().padding(padding),
				color = MyColor.DarkGreyBackground
			)
		}
	}

}