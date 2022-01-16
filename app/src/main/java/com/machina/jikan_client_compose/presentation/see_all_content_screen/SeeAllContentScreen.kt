package com.machina.jikan_client_compose.presentation.see_all_content_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.machina.jikan_client_compose.presentation.see_all_content_screen.data.SeeAllContentViewModel
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun SeeAllContentScreen(
  modifier: Modifier = Modifier,
  viewModel: SeeAllContentViewModel,
  onBackPressed: () -> Boolean
) {
  val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Custom)

  Column(modifier = Modifier.fillMaxSize()) {

  }

}