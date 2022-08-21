package com.machina.jikan_client_compose.presentation.content_search_screen.composable

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.presentation.content_search_screen.data.filter.rememberFilterContentRatingList
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MyShape
import com.machina.jikan_client_compose.ui.theme.Type
import com.machina.jikan_client_compose.ui.theme.Type.bold
import com.machina.jikan_client_compose.ui.theme.Type.darkBlue
import com.machina.jikan_client_compose.ui.theme.Type.onDarkSurface
import com.machina.jikan_client_compose.ui.theme.Type.semiBold
import com.machina.jikan_client_compose.ui.theme.Type.yellow500

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ColumnScope.FilterBottomSheetSearchScreen(
  modifier: Modifier = Modifier
) {
  val localDensity = LocalDensity.current
  val screenHeight = LocalConfiguration.current.screenHeightDp.dp / 3 * 2
  val interactionSource = remember { MutableInteractionSource() }
  val scrollableHeight = remember { mutableStateOf(0.dp) }
  val ratingData = rememberFilterContentRatingList()


  Column(
    modifier = if (scrollableHeight.value > screenHeight) {
      Modifier
        .height(screenHeight)
    } else {
      Modifier
    }
  ) {
    Box(
      modifier = Modifier
        .align(Alignment.CenterHorizontally)
        .padding(top = 8.dp)
        .width(48.dp)
        .height(6.dp)
        .clip(MyShape.RoundedAllPercent50)
        .background(MyColor.Grey)
    )

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Surface(
        modifier = Modifier
          .clip(MyShape.RoundedAllPercent50)
          .clickable {
            val temp = ratingData.value.map {
              it.copy(isChecked = false)
            }
            ratingData.value = temp
          },
        color = Color.Transparent,
      ) {
        Text(
          text = "Reset",
          style = Type.Typography.subtitle1.semiBold().yellow500(),
          modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
        )
      }

      Surface(
        modifier = Modifier
          .clip(MyShape.RoundedAllPercent50)
          .clickable(
            interactionSource = interactionSource,
            indication = rememberRipple(color = MyColor.Yellow500Ripple),
            onClick = { }
          ),
        color = MyColor.Yellow500,
      ) {
        Text(
          text = "Apply",
          style = Type.Typography.subtitle1.semiBold().darkBlue(),
          modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
        )
      }
    }


    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .onGloballyPositioned {
          with(localDensity) {
            scrollableHeight.value = it.size.height.toDp()
          }
        }
    ) {
//      repeat(7) {
        var isExpanded by remember { mutableStateOf(false) }

        AnimatedContent(
          targetState = isExpanded,
          transitionSpec = {
            fadeIn(animationSpec = tween(150, 150)) with
              fadeOut(animationSpec = tween(150, 150)) using
              SizeTransform(clip = true)
          }
        ) { targetExpanded ->
          Column {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clickable {
                  isExpanded = !isExpanded
                }
                .padding(horizontal = 16.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Rating",
                style = Type.Typography.subtitle1.onDarkSurface().bold(),
                modifier = Modifier
                  .weight(1f)
                  .padding(vertical = 12.dp)
              )
              Icon(
                imageVector = if (isExpanded) {
                  Icons.Default.KeyboardArrowUp
                } else {
                  Icons.Default.KeyboardArrowDown
                },
                contentDescription = "Toggle expand",
                tint = MyColor.Grey
              )
            }
            if (targetExpanded) {
              ratingData.value.mapIndexed { index, contentRating ->
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                      val temp = ratingData.value.toMutableList()
                      val newRating = temp[index].copy(isChecked = !temp[index].isChecked)
                      temp[index] = newRating
                      ratingData.value = temp
                    }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Checkbox(
                    checked = contentRating.isChecked,
                    onCheckedChange = null,
                    modifier = Modifier.padding(end = 12.dp)
                  )

                  Text(
                    text = "${contentRating.name} - ${contentRating.description}",
                    style = Type.Typography.subtitle1.onDarkSurface()
                  )
                }
              }
            }
          }
        }
//      }
    }
  }
}