package com.machina.jikan_client_compose.presentation.content_search_screen.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.R
import com.machina.jikan_client_compose.core.constant.Constant
import com.machina.jikan_client_compose.presentation.composable.CustomTextField
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.Type
import com.machina.jikan_client_compose.ui.theme.Type.grey
import com.machina.jikan_client_compose.ui.theme.Type.onDarkSurface

@Composable
fun SearchBoxSearchScreen(
  modifier: Modifier = Modifier,
  isEnabled: Boolean = true,
  searchQuery: String = "",
  placeholder: String = Constant.SEARCH_FIELD_PLACEHOLDER,
  focusRequester: FocusRequester = FocusRequester(),
  onSearchQueryChanged: (String) -> Unit = { },
  onSearchQueryCleared: () -> Unit = { }
) {
  CustomTextField(
    modifier = modifier,
    padding = PaddingValues(12.dp),
    leadingIcon = {
      SearchLeadingIcon(
        size = 16.dp,
        padding = PaddingValues(end = 8.dp)
      )
    },
    trailingIcon = {
      if (searchQuery.isNotEmpty()) {
        SearchTrailingIcon(
          size = 16.dp,
          padding = PaddingValues(start = 8.dp),
          onClick = onSearchQueryCleared
        )
      }
    }
  ) {
    if (isEnabled) {
      BasicTextField(
        modifier = Modifier
          .fillMaxWidth()
          .focusRequester(focusRequester),
        value = searchQuery,
        textStyle = Type.Typography.subtitle1.onDarkSurface(),
        singleLine = true,
        cursorBrush = SolidColor(MyColor.Yellow500),
        onValueChange = { onSearchQueryChanged(it) },
      )
    }

    if (searchQuery.isEmpty()) {
      Text(
        text = placeholder,
        style = Type.Typography.subtitle1.grey()
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