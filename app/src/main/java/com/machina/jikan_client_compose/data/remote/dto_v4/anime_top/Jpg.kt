package com.machina.jikan_client_compose.data.remote.dto_v4.anime_top


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Jpg(
  @SerialName("image_url")
  val imageUrl: String = "",
  @SerialName("small_image_url")
  val smallImageUrl: String = "",
  @SerialName("large_image_url")
  val largeImageUrl: String = ""
)