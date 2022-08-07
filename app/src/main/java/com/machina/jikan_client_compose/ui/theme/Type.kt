package com.machina.jikan_client_compose.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

// Set of Material typography styles to start with
object Type {
  val Typography = Typography(
    h1 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = MySize.Text34,
    ),
    h2 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = MySize.Text30
    ),
    h3 = TextStyle(
      fontWeight = FontWeight.SemiBold,
      fontSize = MySize.Text26
    ),
    h4 = TextStyle(
      fontWeight = FontWeight.SemiBold,
      fontSize = MySize.Text24
    ),
    h5 = TextStyle(
      fontWeight = FontWeight.Medium,
      fontSize = MySize.Text22
    ),
    h6 = TextStyle(
      fontWeight = FontWeight.Medium,
      fontSize = MySize.Text20
    ),
    subtitle1 = TextStyle(
      fontWeight = FontWeight.Medium,
      fontSize = MySize.Text18
    ),
    subtitle2 = TextStyle(
      fontWeight = FontWeight.Medium,
      fontSize = MySize.Text16
    ),
    body1 = TextStyle(
      fontWeight = FontWeight.Normal,
      fontSize = MySize.Text14
    ),
    body2 = TextStyle(
      fontWeight = FontWeight.Normal,
      fontSize = MySize.Text12
    ),
    caption = TextStyle(
      fontWeight = FontWeight.Normal,
      fontSize = MySize.Text11
    ),
    overline = TextStyle(
      fontWeight = FontWeight.Light,
      fontSize = MySize.Text10
    ),

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
  )

  internal fun TextStyle.extraBold(): TextStyle = this.merge(
    TextStyle(fontWeight = FontWeight.ExtraBold)
  )

  internal fun TextStyle.bold(): TextStyle = this.merge(
    TextStyle(fontWeight = FontWeight.Bold)
  )

  internal fun TextStyle.semiBold(): TextStyle = this.merge(
    TextStyle(fontWeight = FontWeight.SemiBold)
  )

  internal fun TextStyle.medium(): TextStyle = this.merge(
    TextStyle(fontWeight = FontWeight.Medium)
  )

  internal fun TextStyle.normal(): TextStyle = this.merge(
    TextStyle(fontWeight = FontWeight.Normal)
  )

  internal fun TextStyle.light(): TextStyle = this.merge(
    TextStyle(fontWeight = FontWeight.Light)
  )

  internal fun TextStyle.extraLight(): TextStyle = this.merge(
    TextStyle(fontWeight = FontWeight.ExtraLight)
  )

  internal fun TextStyle.onDarkSurface(): TextStyle = this.merge(
    TextStyle(color = MyColor.OnDarkSurface)
  )

  internal fun TextStyle.onDarkSurfaceLight(): TextStyle = this.merge(
    TextStyle(color = MyColor.OnDarkSurfaceLight)
  )
}