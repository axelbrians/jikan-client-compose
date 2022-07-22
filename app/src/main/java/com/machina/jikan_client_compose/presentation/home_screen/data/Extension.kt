package com.machina.jikan_client_compose.presentation.home_screen.data

fun AnimeHorizontalContentState.copyKeepData(state: AnimeHorizontalContentState): AnimeHorizontalContentState {
  return AnimeHorizontalContentState(
    data = this.data,
    isLoading = state.isLoading,
    error = state.error
  )
}

