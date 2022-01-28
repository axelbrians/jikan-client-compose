package com.machina.jikan_client_compose.presentation.home_screen.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machina.jikan_client_compose.R
import com.machina.jikan_client_compose.ui.theme.MyColor

@Composable
fun SearchEditText(
  fieldPlaceholder: String = ""
) {
  Text(
    text = fieldPlaceholder,
    style = TextStyle(color = MyColor.Grey, fontSize = 16.sp)
  )
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