package com.machina.jikan_client_compose.presentation.detail_screen.trailer_player

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import timber.log.Timber

@Composable
fun ContentDetailsTrailerPlayer(
  modifier: Modifier = Modifier,
  url: String = ""
) {
  AndroidView(
    factory = {
      WebView(it).apply {
        layoutParams = ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT
        )
        webViewClient = WebViewClient()
        settings.javaScriptEnabled = true
        loadUrl(url)
      }
    },
    modifier = modifier
      .fillMaxWidth(),
    update = {
      it.loadUrl(url)
    }
  )
}