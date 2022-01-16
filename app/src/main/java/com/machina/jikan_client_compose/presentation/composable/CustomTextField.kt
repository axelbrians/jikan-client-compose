package com.machina.jikan_client_compose.presentation.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machina.jikan_client_compose.ui.theme.BlackLighterBackground
import com.machina.jikan_client_compose.ui.theme.Grey
import com.machina.jikan_client_compose.ui.theme.OnDarkSurface
import com.machina.jikan_client_compose.ui.theme.Yellow500

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    fieldValue: String = "",
    fieldPlaceholder: String = "",
    fieldElevation: Dp = 0.dp,
    onFieldValueChange: (String) -> Unit,
    fieldPadding: PaddingValues = PaddingValues(0.dp),
    paddingLeadingIcon: PaddingValues = PaddingValues(0.dp),
    paddingTrailingIcon: PaddingValues = PaddingValues(0.dp),
    leadingIcon: (@Composable() () -> Unit)? = null,
    trailingIcon: (@Composable() () -> Unit)? = null
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = BlackLighterBackground,
        elevation = fieldElevation
    ) {
        Row(
            modifier = Modifier.padding(fieldPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadingIcon != null) {
                Box(modifier = Modifier.padding(paddingLeadingIcon)) {
                    leadingIcon()
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                BasicTextField(
                    value = fieldValue,
                    onValueChange = onFieldValueChange,
                    singleLine = true,
                    cursorBrush = SolidColor(Yellow500),
                    textStyle = TextStyle(color = OnDarkSurface, fontSize = 16.sp),
                    modifier = Modifier.fillMaxWidth()
                )

                if (fieldValue.isEmpty()) {
                    Text(text = fieldPlaceholder, style = TextStyle(color = Grey, fontSize = 16.sp))
                }
            }

            if (trailingIcon != null) {
                Box(modifier = Modifier.padding(paddingTrailingIcon)) {
                    trailingIcon()
                }
            }
        }
    }
}

