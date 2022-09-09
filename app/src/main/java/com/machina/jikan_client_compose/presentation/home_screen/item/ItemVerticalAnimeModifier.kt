package com.machina.jikan_client_compose.presentation.home_screen.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object ItemVerticalAnimeModifier {
  val default = Modifier
    .width(140.dp)

  val fillParentWidth = Modifier
    .fillMaxWidth()

  val ThumbnailHeightDefault = 190.dp
  val ThumbnailHeightGrid = 160.dp
  val ThumbnailHeightSmall = 150.dp

  object HorizontalArrangement {
    val Default = Arrangement.spacedBy(12.dp)
  }
}