package com.machina.jikan_client_compose.presentation.content_search_screen.composable.bottom_sheet

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyIcons
import com.machina.jikan_client_compose.ui.theme.Type
import com.machina.jikan_client_compose.ui.theme.Type.darkBlue
import com.machina.jikan_client_compose.ui.theme.Type.medium

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandableFloatingButtonSearchScreen(
  modifier: Modifier = Modifier,
  isExpanded: Boolean = true,
  onClick: () -> Unit = { }
) {
  Row(
    modifier = modifier
      .clip(RoundedCornerShape(10.dp))
      .background(MyColor.Yellow500)
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(color = MyColor.Yellow500Ripple),
        onClick = onClick
      )
      .padding(12.dp)
,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.End
  ) {
    Icon(
      modifier = Modifier.size(24.dp),
      imageVector = ImageVector.vectorResource(MyIcons.Solid.Filter),
      contentDescription = "Filter",
      tint = MyColor.DarkBlueBackground
    )
    AnimatedContent(
      targetState = isExpanded,
      transitionSpec = {
        fadeIn(animationSpec = tween(300)) with
          fadeOut(animationSpec = tween(300)) using
          SizeTransform(clip = true)
      }
    ) { targetExpanded ->
      if (targetExpanded) {
        Text(
          modifier = Modifier.padding(start = 8.dp),
          text = "Filter",
          style = Type.Typography.subtitle2.medium().darkBlue()
        )
      }
    }
  }
}