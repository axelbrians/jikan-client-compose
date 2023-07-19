package com.machina.jikan_client_compose.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

// Set of Material typography styles to start with
object Type {
	val Typography = Typography(

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

	fun TextStyle.darkBlue(): TextStyle = this.merge(
		TextStyle(color = MyColor.DarkBlueBackground)
	)

	fun TextStyle.darkGrey(): TextStyle = this.merge(
		TextStyle(color = MyColor.DarkGreyBackground)
	)

	fun TextStyle.grey(): TextStyle = this.merge(
		TextStyle(color = MyColor.Grey)
	)

	fun TextStyle.yellow500(): TextStyle = this.merge(
		TextStyle(color = MyColor.Yellow500)
	)

	fun TextStyle.alignJustify(): TextStyle = this.merge(
		TextStyle(textAlign = TextAlign.Justify)
	)

	fun TextStyle.alignCenter(): TextStyle = this.merge(
		TextStyle(textAlign = TextAlign.Center)
	)

	fun TextStyle.alignLeft(): TextStyle = this.merge(
		TextStyle(textAlign = TextAlign.Left)
	)

	fun TextStyle.alignRight(): TextStyle = this.merge(
		TextStyle(textAlign = TextAlign.Right)
	)
}