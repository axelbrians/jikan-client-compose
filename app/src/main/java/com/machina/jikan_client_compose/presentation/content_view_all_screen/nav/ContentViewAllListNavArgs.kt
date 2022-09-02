package com.machina.jikan_client_compose.presentation.content_view_all_screen.nav

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContentViewAllListNavArgs(
	val title: String,
	val url: String,
	val params: Map<String, String> = mapOf()
): Parcelable