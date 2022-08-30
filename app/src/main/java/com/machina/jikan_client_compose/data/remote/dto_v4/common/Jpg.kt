package com.machina.jikan_client_compose.data.remote.dto_v4.common


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
) {
  companion object {
    fun Jpg.getHighestResImgUrl(): String {
      return if (largeImageUrl.isNotEmpty()) {
        largeImageUrl
      } else if (imageUrl.isNotEmpty()) {
        imageUrl
      } else {
        smallImageUrl
      }
    }
  }

}