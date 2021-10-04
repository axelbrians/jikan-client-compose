package com.machina.jikan_client_compose.ui.view.home_screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machina.jikan_client_compose.ui.theme.*

@Composable
fun HomeScreen() {
    
    Scaffold {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 12.dp),
                fieldPlaceholder = "Try 'One Piece'",
                fieldHorizontalPadding = 12.dp,
                fieldVerticalPadding = 12.dp,
                leadingIcon = { Icon(Icons.Default.Search, "Search") },
                paddingLeadingIconEnd = 8.dp
            )

            Divider(color = BlackLighterBackground, thickness = 1.dp, modifier = Modifier.padding(0.dp, 12.dp, 0.dp, 8.dp))


            Row(
                modifier = Modifier.padding(18.dp, 4.dp, 18.dp, 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Trending Now",
                    style = TextStyle(color = Yellow500, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                )

                IconButton(onClick = { Log.d("", "") }) {
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "See all", tint = Grey)
                }
            }

            LazyRow(
                contentPadding = PaddingValues(12.dp, 0.dp, 12.dp, 0.dp)
            ) {
                items(5, key = { it }) {
                    ItemAnime(
                        modifier = Modifier.width(140.dp)
                            .padding(12.dp, 0.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    fieldElevation: Dp = 0.dp,
    fieldPlaceholder: String = "",
    fieldVerticalPadding: Dp = 0.dp,
    fieldHorizontalPadding: Dp = 0.dp,
    paddingLeadingIconEnd: Dp = 0.dp,
    paddingTrailingIconStart: Dp = 0.dp,
    leadingIcon: (@Composable() () -> Unit)? = null,
    trailingIcon: (@Composable() () -> Unit)? = null
) {
    var searchQuery by remember {
        mutableStateOf("")
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = BlackLighterBackground,
        elevation = fieldElevation
    ) {
        Row(
            modifier = Modifier.padding(fieldHorizontalPadding, fieldVerticalPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadingIcon != null) { leadingIcon() }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = paddingLeadingIconEnd, end = paddingTrailingIconStart)
            ) {
                BasicTextField(value = searchQuery, onValueChange = { searchQuery = it},
                    singleLine = true,
                    cursorBrush = SolidColor(Yellow500),
                    textStyle = TextStyle(color = OnDarkSurface, fontSize = 16.sp)
                )

                if (searchQuery.isEmpty()) {
                    Text(text = fieldPlaceholder, style = TextStyle(color = Grey, fontSize = 16.sp))
                }
            }

            if (trailingIcon != null) { trailingIcon() }
        }
    }
}