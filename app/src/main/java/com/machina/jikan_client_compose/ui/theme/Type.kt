package com.machina.jikan_client_compose.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

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
      fontSize = MySize.Text22
    ),
    h5 = TextStyle(
      fontWeight = FontWeight.Medium,
      fontSize = MySize.Text20
    ),
    h6 = TextStyle(
      fontWeight = FontWeight.Medium,
      fontSize = MySize.Text18
    ),
    subtitle1 = TextStyle(
      fontWeight = FontWeight.Medium,
      fontSize = MySize.Text16
    ),
    subtitle2 = TextStyle(
      fontWeight = FontWeight.Medium,
      fontSize = MySize.Text14
    ),
    body1 = TextStyle(
      fontWeight = FontWeight.Normal,
      fontSize = MySize.Text13
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

  fun TextStyle.extraBold(): TextStyle = this.merge(
    TextStyle(fontWeight = FontWeight.ExtraBold)
  )

  fun TextStyle.bold(): TextStyle = this.merge(
    TextStyle(fontWeight = FontWeight.Bold)
  )

  fun TextStyle.semiBold(): TextStyle = this.merge(
    TextStyle(fontWeight = FontWeight.SemiBold)
  )

  fun TextStyle.medium(): TextStyle = this.merge(
    TextStyle(fontWeight = FontWeight.Medium)
  )

  fun TextStyle.normal(): TextStyle = this.merge(
    TextStyle(fontWeight = FontWeight.Normal)
  )

  fun TextStyle.light(): TextStyle = this.merge(
    TextStyle(fontWeight = FontWeight.Light)
  )

  fun TextStyle.extraLight(): TextStyle = this.merge(
    TextStyle(fontWeight = FontWeight.ExtraLight)
  )

  fun TextStyle.onDarkSurface(): TextStyle = this.merge(
    TextStyle(color = MyColor.OnDarkSurface)
  )

  fun TextStyle.onDarkSurfaceLight(): TextStyle = this.merge(
    TextStyle(color = MyColor.OnDarkSurfaceLight)
  )

  fun TextStyle.justify(): TextStyle = this.merge(
    TextStyle(textAlign = TextAlign.Justify)
  )

  fun TextStyle.center(): TextStyle = this.merge(
    TextStyle(textAlign = TextAlign.Center)
  )

  fun TextStyle.left(): TextStyle = this.merge(
    TextStyle(textAlign = TextAlign.Left)
  )

  fun TextStyle.right(): TextStyle = this.merge(
    TextStyle(textAlign = TextAlign.Right)
  )
}