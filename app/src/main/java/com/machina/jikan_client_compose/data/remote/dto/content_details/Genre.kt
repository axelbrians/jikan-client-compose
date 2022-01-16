package com.machina.jikan_client_compose.data.remote.dto.content_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    @SerialName("mal_id")
    val malId: Int,

    @SerialName("type")
    val type: String,

    @SerialName("name")
    val name: String,

    @SerialName("url")
    val url: String
)