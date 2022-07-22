package com.machina.jikan_client_compose.ui.shimmer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.unclippedBoundsInWindow

@Composable
fun rememberShimmerCustomBounds(): Shimmer {
  return rememberShimmer(shimmerBounds = ShimmerBounds.Custom)
}

/**
 * <p>Simplified function to update ShimmerBounds.</p>
 * <p>Note that when using this simplified [Modifier], {@link Modifier##onGloballyPosition} cannot
 * be used again.</p>
 */
@Stable
fun Modifier.onUpdateShimmerBounds(
  shimmerInstance: Shimmer
) = this.then(
  onGloballyPositioned { value: LayoutCoordinates ->
    val position = value.unclippedBoundsInWindow()
    shimmerInstance.updateBounds(position)
  }
)