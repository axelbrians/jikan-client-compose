package com.machina.jikan_client_compose.data.remote.dto_v4.manga


import com.machina.jikan_client_compose.data.remote.dto_v4.common.Prop
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Published(
  @SerialName("from")
  val from: String = "",
  @SerialName("to")
  val to: String = "",
  @SerialName("prop")
  val prop: Prop = Prop(),
  @SerialName("string")
  val string: String = ""
)