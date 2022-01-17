package com.machina.jikan_client_compose.presentation.detail_screen.composable

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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.machina.jikan_client_compose.presentation.detail_screen.data.ContentDetailsState
import com.machina.jikan_client_compose.ui.theme.MyColor

@ExperimentalAnimationApi
@Composable
fun ContentDetailsSynopsis(
  state: ContentDetailsState?
) {
  var expanded by remember { mutableStateOf(false) }
  val contentSynopsis = resolveContentDetailsSynopsis(state)




  AnimatedContent(
    targetState = expanded,
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
          style = TextStyle(
            color = MyColor.OnDarkSurface,
            fontSize = 13.sp,
            textAlign = TextAlign.Justify
          ),
          modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp)
        )

        IconButton(
          onClick = { expanded = !expanded },
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
          style = TextStyle(
            color = MyColor.OnDarkSurface,
            fontSize = 13.sp,
            textAlign = TextAlign.Justify
          ),
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
                  MyColor.BlackBackground.copy(alpha = 0F),
                  MyColor.BlackBackground.copy(alpha = 0.9F),
                  MyColor.BlackBackground
                )
              )
            )
        ) {
          IconButton(
            onClick = { expanded = !expanded },
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