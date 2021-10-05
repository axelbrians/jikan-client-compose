package com.machina.jikan_client_compose.data.response

import com.google.gson.annotations.SerializedName
import com.machina.jikan_client_compose.data.model.AnimeTop

data class AnimeTopResponse(
    @SerializedName("request_hash")
    val requestHash: String,

    @SerializedName("request_cached")
    val requestCached: Boolean,

    @SerializedName("request_cache_expiry")
    val requestCacheExpiry: Int,

    @SerializedName("top")
    val top: List<AnimeTop>
)
