package com.machina.jikan_client_compose.presentation.content_detail_screen.three_column

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.presentation.content_detail_screen.data.ContentDetailsState
import com.machina.jikan_client_compose.ui.theme.MyColor
import com.machina.jikan_client_compose.ui.theme.MySize

@Composable
fun ContentDetailsThreeColumnSection(
  modifier: Modifier = Modifier,
  state: ContentDetailsState = ContentDetailsState()
) {

  val first = (state.data?.score ?: "-").toString()
  val second = (state.data?.episodes ?: "-").toString()
  val third = (state.data?.duration ?: "-").toString()

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .height(IntrinsicSize.Min)
      .then(modifier),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    TwoRowTextComp(title = "Score", subtitle = first)
    VerticalDivider()
    TwoRowTextComp(title = "Episodes", subtitle = second)
    VerticalDivider()
    TwoRowTextComp(title = "Duration", subtitle = third)
  }
}

@Composable
private fun RowScope.TwoRowTextComp(
  modifier: Modifier = Modifier,
  title: String,
  subtitle: String
) {
  Column(
    modifier = Modifier.weight(weight = 1f),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = title,
      style = TextStyle(
        fontSize = MySize.Text14,
        fontWeight = FontWeight.Medium,
        color = MyColor.OnDarkSurfaceLight,
      ))
    Text(
      text = subtitle,
      style = TextStyle(
        fontSize = MySize.Text14,
        fontWeight = FontWeight.Normal,
        color = MyColor.OnDarkSurface,
      ))
  }
}

@Composable
private fun RowScope.VerticalDivider(modifier: Modifier = Modifier) {
  Divider(
    color = MyColor.Yellow500,
    modifier = Modifier
      .fillMaxHeight(fraction = 0.9f)
      .width(2.dp)
  )
}

@Preview(widthDp = 280)
@Composable
fun ContentDetailsThreeColumnSectionPreview() {
  ContentDetailsThreeColumnSection()
}

