package com.machina.jikan_client_compose.presentation.home_screen.data

fun AnimeHorizontalListContentState.copyKeepData(state: AnimeHorizontalListContentState): AnimeHorizontalListContentState {
  return AnimeHorizontalListContentState(
    data = this.data,
    isLoading = state.isLoading,
    error = state.error
  )
}

