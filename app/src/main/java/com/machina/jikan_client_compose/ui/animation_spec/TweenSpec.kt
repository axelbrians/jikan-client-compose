package com.machina.jikan_client_compose.ui.animation_spec

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween

object TweenSpec {
  fun <T> defaultEasing(): AnimationSpec<T> {
    return tween(
      durationMillis = 300,
      easing = LinearEasing
    )
  }
}