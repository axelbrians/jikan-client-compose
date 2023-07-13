package com.machina.jikan_client_compose.presentation.content_view_all_screen.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machina.jikan_client_compose.ui.theme.MyColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentViewAllListScreenToolbar(
  modifier: Modifier = Modifier,
  title: String,
  onClick: () -> Unit,
  trailingIcon: (@Composable () -> Unit)? = null
) {
  TopAppBar(
    modifier = modifier
      .fillMaxWidth(),
    navigationIcon = {
      IconButton(onClick = onClick) {
        Icon(
          imageVector = Icons.Default.ArrowBack,
          contentDescription = "Back", tint = MyColor.OnDarkSurfaceLight
        )
      }
    },
    title = {
      Text(
        text = title,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = TextStyle(
          color = MyColor.OnDarkSurfaceLight,
          fontWeight = FontWeight.Bold,
          fontSize = 18.sp
        ),
        modifier = Modifier.padding(horizontal = 8.dp)
      )
    },
    actions = {
      if (trailingIcon != null) {
        trailingIcon()
      }
    }
  )
}