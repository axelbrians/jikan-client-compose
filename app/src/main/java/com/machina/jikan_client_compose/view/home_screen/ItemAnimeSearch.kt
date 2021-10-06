package com.machina.jikan_client_compose.view.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.machina.jikan_client_compose.data.model.ContentSearch
import com.machina.jikan_client_compose.ui.theme.OnDarkSurface
import com.machina.jikan_client_compose.ui.theme.Yellow500
import com.machina.jikan_client_compose.view.composable.CenterCircularProgressIndicator

@ExperimentalCoilApi
@Composable
fun ItemAnimeSearch(
    modifier: Modifier = Modifier,
    data: ContentSearch
) {

    val painter = rememberImagePainter(
        data = data.imageUrl,
        builder = {
            crossfade(true)
        }
    )

    Row(
        modifier = modifier
    ) {
        Box (
            modifier = Modifier
                .width(120.dp)
                .height(160.dp)
        ) {
            if (painter.state is ImagePainter.State.Loading) {
                CenterCircularProgressIndicator(
                    strokeWidth = 2.dp,
                    size = 20.dp,
                    color = Yellow500
                )
            }
            Image(
                painter = painter,
                contentDescription = "Thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .height(160.dp)
                .padding(8.dp, 0.dp, 0.dp, 4.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = data.title,
                    style = TextStyle(color = OnDarkSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = 2.dp)
                )

                Text(
                    text = data.synopsis,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(color = OnDarkSurface, fontSize = 12.sp, fontWeight = FontWeight.Normal),
                    modifier = Modifier
                        .padding(bottom = 3.dp)
                )
            }

            Column {
                Text(
                    text = "${data.score}",
                    style = TextStyle(color = OnDarkSurface, fontSize = 13.sp, fontWeight = FontWeight.Normal),
                )

                Text(
                    text = "${data.episodesCount} episodes",
                    style = TextStyle(color = OnDarkSurface, fontSize = 13.sp, fontWeight = FontWeight.Normal),
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
        }
    }
}