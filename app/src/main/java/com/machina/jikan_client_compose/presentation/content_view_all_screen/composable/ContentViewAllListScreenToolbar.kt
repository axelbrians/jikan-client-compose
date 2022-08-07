package com.machina.jikan_client_compose.presentation.content_view_all_screen.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machina.jikan_client_compose.ui.theme.MyColor

@Composable
fun ContentViewAllListScreenToolbar(
  modifier: Modifier = Modifier,
  title: String,
  onClick: () -> Unit
) {
  TopAppBar(
    modifier = modifier
      .fillMaxWidth(),
    backgroundColor = MyColor.DarkGreyBackground
  ) {
    IconButton(onClick = onClick) {
      Icon(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = "Back", tint = MyColor.OnDarkSurfaceLight
      )
    }

    Text(
      text = title,
      overflow = TextOverflow.Ellipsis,
      maxLines = 1,
      style = TextStyle(
        color = MyColor.OnDarkSurfaceLight,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
      ),
      modifier = Modifier
        .weight(1f)
        .padding(start = 8.dp, end = 12.dp)
    )
  }
}