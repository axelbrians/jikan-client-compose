package com.machina.jikan_client_compose.ui.view.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machina.jikan_client_compose.R
import com.machina.jikan_client_compose.ui.theme.OnDarkSurface


@Composable
fun ItemAnime(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Image(
            painterResource(R.drawable.yuqi),
            contentDescription = "Thumbnail",
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(190.dp)
                .clip(RoundedCornerShape(6.dp))
        )

        Text(
            text = "Attack on Titan",
            style = TextStyle(color = OnDarkSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(0.dp, 6.dp, 0.dp, 0.dp)
        )

        Text(
            text = "Action",
            style = TextStyle(color = OnDarkSurface, fontSize = 14.sp, fontWeight = FontWeight.Normal)
        )
    }
}