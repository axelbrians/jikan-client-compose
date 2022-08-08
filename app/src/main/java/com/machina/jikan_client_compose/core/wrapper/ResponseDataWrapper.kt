package com.machina.jikan_client_compose.core.wrapper

import kotlinx.serialization.Serializable

@Serializable
data class ResponseDataWrapper<T>(
  val data: T
)
