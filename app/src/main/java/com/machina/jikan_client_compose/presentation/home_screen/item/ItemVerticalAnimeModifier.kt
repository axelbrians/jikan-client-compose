package com.machina.jikan_client_compose.presentation.home_screen.item

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object ItemVerticalAnimeModifier {
  val default = Modifier
    .width(140.dp)
    .padding(6.dp, 4.dp)

  val fillParentWidth = Modifier
    .fillMaxWidth()
    .padding(6.dp, 4.dp)

}