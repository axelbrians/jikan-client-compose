package com.machina.jikan_client_compose.presentation.content_detail_screen.composable

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.machina.jikan_client_compose.presentation.content_detail_screen.data.ContentDetailsState
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.Type
import com.machina.jikan_client_compose.ui.theme.Type.alignJustify
import com.machina.jikan_client_compose.ui.theme.Type.onDarkSurface

@ExperimentalAnimationApi
@Composable
fun ContentDetailsSynopsis(
  state: ContentDetailsState?,
  isExpanded: Boolean,
  onClick: (Boolean) -> Unit,
) {
  val contentSynopsis = resolveContentDetailsSynopsis(state)

  AnimatedContent(
    targetState = isExpanded,
    transitionSpec = {
      expandVertically(animationSpec = tween(150, 150), initialHeight = { it }) with
        shrinkVertically(animationSpec = tween(150, 150), targetHeight = { it }) using
        SizeTransform(clip = true)
    }
  ) { targetExpanded ->
    if (targetExpanded) {
      Column {
        Text(
          text = if (state?.data?.synopsis != null) contentSynopsis else "",
          style = Type.Typography.body1.onDarkSurface().alignJustify(),
          modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp)
        )

        IconButton(
          onClick = { onClick(!isExpanded) },
          modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
          Icon(
            imageVector = Icons.Default.KeyboardArrowUp,
            contentDescription = "Shrink",
            tint = MyColor.Grey
          )
        }
      }
    } else {
      Box {
        Text(
          text = if (state?.data?.synopsis != null) contentSynopsis else "",
          maxLines = 5,
          overflow = TextOverflow.Ellipsis,
          style = Type.Typography.body1.onDarkSurface().alignJustify(),
          modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 4.dp)
        )
        Box(
          modifier = Modifier
            .zIndex(1F)
            .fillMaxSize()
            .align(Alignment.BottomCenter)
            .background(
              brush = Brush.verticalGradient(
                colors = listOf(
                  MyColor.DarkBlueBackground.copy(alpha = 0F),
                  MyColor.DarkBlueBackground.copy(alpha = 0.9F),
                  MyColor.DarkBlueBackground
                )
              )
            )
        ) {
          IconButton(
            onClick = { onClick(!isExpanded) },
            modifier = Modifier
              .align(Alignment.BottomCenter)
              .zIndex(2F)
          ) {
            Icon(
              imageVector = Icons.Default.KeyboardArrowDown,
              contentDescription = "Expand",
              tint = MyColor.OnDarkSurface
            )
          }
        }

      }
    }
  }
}

private fun resolveContentDetailsSynopsis(state: ContentDetailsState?): String {
  var synopsis = "${state?.data?.synopsis}"
  with(state?.data) {
    if (this == null) {
      return@with
    }

    if (titleJapanese.isNotBlank()) {
      synopsis += "\n\nAlternate title: $titleJapanese"
    }
    if (titleEnglish.isNotBlank()) {
      synopsis += ", $titleEnglish"
    }

    titleSynonyms.forEach { title ->
      synopsis += ", $title"
    }
  }

  return synopsis
}