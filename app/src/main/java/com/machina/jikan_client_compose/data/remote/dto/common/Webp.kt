package com.machina.jikan_client_compose.data.remote.dto.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Webp(
  @SerialName("image_url")
  val imageUrl: String = "",
  @SerialName("small_image_url")
  val smallImageUrl: String = "",
  @SerialName("large_image_url")
  val largeImageUrl: String = ""
)