package com.machina.jikan_client_compose.presentation.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.presentation.content_search_screen.composable.SearchEditText
import com.machina.jikan_client_compose.ui.theme.MyColor

@Composable
fun CustomTextField(
  modifier: Modifier = Modifier,
  elevation: Dp = 0.dp,
  padding: PaddingValues = PaddingValues(0.dp),
  content: (@Composable () -> Unit)? = null,
  leadingIcon: (@Composable () -> Unit)? = null,
  trailingIcon: (@Composable () -> Unit)? = null
) {
  Surface(
    modifier = modifier,
    shape = RoundedCornerShape(8.dp),
    color = MyColor.BlackLighterBackground,
    elevation = elevation
  ) {
    Row(
      modifier = Modifier.padding(padding),
      verticalAlignment = Alignment.CenterVertically
    ) {
      if (leadingIcon != null) {
        leadingIcon()
      }

      Box(
        modifier = Modifier
          .weight(1f)
      ) {
        if (content != null) {
          content()
        }
      }


      if (trailingIcon != null) {
        trailingIcon()
      }
    }
  }
}

@Preview(
  widthDp = 280
)
@Composable
fun CustomTextFieldPreview() {
  CustomTextField(
    content = { SearchEditText() },
    leadingIcon = {
      Icon(imageVector = Icons.Default.Call, contentDescription = "")
    },
    trailingIcon = {
      Icon(imageVector = Icons.Default.Home, contentDescription = "")
    },
    padding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
  )
}
