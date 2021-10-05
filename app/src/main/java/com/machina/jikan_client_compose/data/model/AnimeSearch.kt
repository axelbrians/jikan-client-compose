package com.machina.jikan_client_compose.data.model

import com.google.gson.annotations.SerializedName

data class AnimeSearch(
    @SerializedName("mal_id")
    val malId: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("image_url")
    val imageUrl: String,

    @SerializedName("synopsis")
    val synopsis: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("episodes")
    val episodesCount: Int,

    @SerializedName("start_date")
    val startDate: String,

    @SerializedName("end_date")
    val endDate: String,

    @SerializedName("members")
    val members: Int,

    @SerializedName("score")
    val score: Double,

    @SerializedName("rated")
    val rated: String
)
