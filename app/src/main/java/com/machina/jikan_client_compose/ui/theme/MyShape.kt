package com.machina.jikan_client_compose.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

object MyShape {
    val ThemeShapes = Shapes(
        extraSmall = RoundedCornerShape(4.dp),
        small = RoundedCornerShape(6.dp),
        medium = RoundedCornerShape(8.dp),
        large = RoundedCornerShape(12.dp),
        extraLarge = RoundedCornerShape(16.dp)
    )

    val Rounded6 = RoundedCornerShape(6.dp)
    val Rounded12 = RoundedCornerShape(12.dp)
    val RoundedAllPercent50 = RoundedCornerShape(50)
}
