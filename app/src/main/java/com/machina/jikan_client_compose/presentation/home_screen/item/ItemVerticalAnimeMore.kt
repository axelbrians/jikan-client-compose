package com.machina.jikan_client_compose.presentation.home_screen.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.core.constant.Constant
import com.machina.jikan_client_compose.ui.theme.*
import com.machina.jikan_client_compose.ui.theme.Type.alignCenter
import com.machina.jikan_client_compose.ui.theme.Type.bold
import com.machina.jikan_client_compose.ui.theme.Type.onDarkSurface

@Composable
fun ItemVerticalAnimeMore(
  modifier: Modifier = Modifier,
  thumbnailHeight: Dp = 190.dp,
  onClick: () -> Unit
) {

  Column(
    modifier = modifier
      .clip(MyShape.Rounded12)
      .clickable { onClick() }
  ) {
    Column(
      modifier = Modifier
        .clip(MyShape.Rounded12)
        .height(thumbnailHeight)
        .background(MyColor.DarkGreyBackground),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
    ) {
      Icon(
        imageVector = ImageVector.vectorResource(id = MyIcons.Filled.ChevronDown),
        contentDescription = "",
        tint = MyColor.OnDarkSurface,
        modifier = Modifier
          .size(32.dp)
          .padding(bottom = 6.dp)
          .rotate(-90f)
      )
      Text(
        text = "See all",
        style = Type.Typography.body2.bold().onDarkSurface().alignCenter(),
        modifier = Modifier.fillMaxWidth()
      )
    }

    Text(
      text = "\n",
      style = MyType.Body2.Bold.OnDarkSurface
    )
//
//    Text(
//      text = "${data.score}",
//      modifier = Modifier.padding(bottom = 3.dp),
//      style = TextStyle(
//        color = MyColor.OnDarkSurface,
//        fontSize = 13.sp,
//        fontWeight = FontWeight.Normal
//      )
//    )
  }
}

fun LazyListScope.showItemVerticalAnimeMoreWhenPastLimit(
  modifier: Modifier = Modifier,
  thumbnailHeight: Dp = 190.dp,
  limit: Int = Constant.HORIZONTAL_CONTENT_LIMIT,
  size: Int = 0,
  onClick: () -> Unit
) {
  if (size > limit) {
    item {
      ItemVerticalAnimeMore(
        modifier = ItemVerticalAnimeModifier.default,
        onClick = onClick
      )
    }
  }
}