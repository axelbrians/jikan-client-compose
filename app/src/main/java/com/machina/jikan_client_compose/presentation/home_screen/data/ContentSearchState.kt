package com.machina.jikan_client_compose.presentation.home_screen.data

import android.os.Parcelable
import com.machina.jikan_client_compose.domain.model.ContentSearch
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContentSearchState(
  val data: List<ContentSearch> = listOf(),
  val isLoading: Boolean = false,
  val error: String? = null
): Parcelable