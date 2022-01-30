package com.machina.jikan_client_compose.data.remote.dto_v4.anime


import com.machina.jikan_client_compose.data.remote.dto_v4.anime.Prop
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Aired(
  @SerialName("from")
  val from: String? = null,
  @SerialName("to")
  val to: String? = null,
  @SerialName("prop")
  val prop: Prop = Prop()
)