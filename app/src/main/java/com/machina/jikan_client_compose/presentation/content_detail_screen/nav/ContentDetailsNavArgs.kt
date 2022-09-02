package com.machina.jikan_client_compose.presentation.content_detail_screen.nav

import android.os.Parcelable
import com.machina.jikan_client_compose.core.enums.ContentType
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContentDetailsNavArgs(
	val malId: Int,
	val contentType: ContentType
) : Parcelable