package com.machina.jikan_client_compose.data.model

import com.google.gson.annotations.SerializedName

data class ContentSearch(
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

    /* Optional based on ContentType */

    // Anime
    @SerializedName("airing")
    val isAiring: Boolean,

    @SerializedName("rated")
    val rated: String?,

    @SerializedName("episodes")
    val episodesCount: Int,

    // Manga
    @SerializedName("publishing")
    val isPublishing: Boolean,

    @SerializedName("type")
    val type: String,

    @SerializedName("chapters")
    val chapters: Int,

    @SerializedName("volumes")
    val volumes: Int,

    /* - - - - - - - - - */


    @SerializedName("start_date")
    val startDate: String?,

    @SerializedName("end_date")
    val endDate: String?,

    @SerializedName("members")
    val members: Int,

    @SerializedName("score")
    val score: Double
)
