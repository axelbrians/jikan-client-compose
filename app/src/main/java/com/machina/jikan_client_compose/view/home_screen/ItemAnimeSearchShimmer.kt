package com.machina.jikan_client_compose.view.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.machina.jikan_client_compose.ui.theme.Grey
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun ItemAnimeSearchShimmer(
    shimmerInstance: Shimmer
) {
    Row(
        modifier = Modifier
            .padding(12.dp, 8.dp)
    ) {
        Box (
            modifier = Modifier
                .shimmer(shimmerInstance)
                .width(120.dp)
                .height(160.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color = Grey)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .height(160.dp)
                .padding(8.dp, 0.dp, 0.dp, 4.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier
                .shimmer(shimmerInstance)
            ) {
                Box(modifier = Modifier
                    .width(72.dp)
                    .height(18.dp)
                    .background(color = Grey))
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp)
                    .height(18.dp)
                    .background(color = Grey))
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp)
                    .height(18.dp)
                    .background(color = Grey))
            }

            Column(
                modifier = Modifier
                .shimmer(shimmerInstance)
            ) {
                Box(modifier = Modifier
                    .padding(top = 2.dp)
                    .width(52.dp)
                    .height(18.dp)
                    .background(color = Grey))

                Box(modifier = Modifier
                    .padding(top = 2.dp)
                    .width(72.dp)
                    .height(18.dp)
                    .background(color = Grey))
            }

        }
    }
}