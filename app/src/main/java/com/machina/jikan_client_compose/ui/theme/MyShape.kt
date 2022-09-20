package com.machina.jikan_client_compose.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

object MyShape {
    val ThemeShapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    val Rounded6 = RoundedCornerShape(6.dp)
    val Rounded12 = RoundedCornerShape(12.dp)
    val RoundedAllPercent50 = RoundedCornerShape(50)
}
