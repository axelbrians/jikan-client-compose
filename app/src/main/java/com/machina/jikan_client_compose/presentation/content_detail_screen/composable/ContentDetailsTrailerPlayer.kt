package com.machina.jikan_client_compose.presentation.content_detail_screen.composable

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter

private enum class TrailerState {
  THUMBNAIL,
  LOADING,
  FINISHED
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ContentDetailsTrailerPlayer(
  modifier: Modifier = Modifier,
  trailerUrl: String = "",
  coilPainter: ImagePainter = rememberImagePainter("")
) {
  val trailerState = remember { mutableStateOf(TrailerState.THUMBNAIL) }

//  Box(modifier = modifier) {
//    if (trailerState.value == TrailerState.THUMBNAIL) {
//      Box(modifier = Modifier.fillMaxSize()) {
//        Image(
//          painter = coilPainter,
//          contentDescription = "Trailer Thumbnail",
//          contentScale = ContentScale.Crop,
//          modifier = Modifier.fillMaxSize()
//        )
//
//        TextButton(onClick = { /*TODO*/ }) {
//
//        }
//      }
//    } else {
      AndroidView(
        factory = {
          WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
              ViewGroup.LayoutParams.MATCH_PARENT,
              ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = object : WebViewClient() {
              override fun onPageFinished(view: WebView?, url: String?) {
                if (trailerUrl == url) {
                  trailerState.value = TrailerState.FINISHED
                }
              }

              override fun onLoadResource(view: WebView?, url: String?) {
                if (trailerUrl == url) {
                  trailerState.value = TrailerState.LOADING
                }
              }
            }

            settings.javaScriptEnabled = true
          }
        },
        modifier = modifier
          .fillMaxSize(),
        update = {
          it.loadUrl(trailerUrl)
        }
      )
//    }

//  }
}