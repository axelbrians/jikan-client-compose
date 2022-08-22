package com.machina.jikan_client_compose.presentation.content_search_screen.composable.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.Type
import com.machina.jikan_client_compose.ui.theme.Type.onDarkSurface

@Composable
fun FilterSelectable(
  modifier: Modifier = Modifier,
  text: String = "",
  isSelected: Boolean = false,
  onSelect: () -> Unit = { }
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .clickable { onSelect() }
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    if (isSelected) {
      Icon(
        imageVector = Icons.Default.Check,
        contentDescription = "Is selected",
        tint = MyColor.Grey,
        modifier = Modifier.size(24.dp)
      )
    } else {
      Spacer(Modifier.size(24.dp))
    }
    Spacer(Modifier.size(12.dp))
    Text(
      text = text,
      style = Type.Typography.subtitle1.onDarkSurface()
    )
  }
}