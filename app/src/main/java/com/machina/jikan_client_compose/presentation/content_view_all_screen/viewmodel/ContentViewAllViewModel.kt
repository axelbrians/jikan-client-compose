package com.machina.jikan_client_compose.presentation.content_view_all_screen.viewmodel

import androidx.compose.runtime.State
import com.machina.jikan_client_compose.presentation.home_screen.data.AnimeHorizontalContentState

interface ContentViewAllViewModel {

  val contentState: State<AnimeHorizontalContentState>

  fun getNextContentPart()
}