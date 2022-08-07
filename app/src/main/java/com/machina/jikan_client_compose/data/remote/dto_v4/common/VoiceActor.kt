package com.machina.jikan_client_compose.data.remote.dto_v4.common

import kotlinx.serialization.SerialName

data class VoiceActor(
  @SerialName("person")
  val person: Person = Person(),
  @SerialName("language")
  val language: String = ""
)
